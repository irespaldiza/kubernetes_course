package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"
	"strings"
	"time"

	_ "github.com/lib/pq"
)

type order struct {
	ID        int    `json:"id"`
	ProductID int    `json:"product_id"`
	Customer  string `json:"customer"`
	Status    string `json:"status"`
}

type product struct {
	ID    int     `json:"id"`
	Name  string  `json:"name"`
	Price float64 `json:"price"`
}

func main() {
	port := getEnv("PORT", "8080")
	databaseURL := os.Getenv("DATABASE_URL")
	catalogURL := strings.TrimRight(getEnv("CATALOG_URL", "http://catalog-service:8000"), "/")

	var db *sql.DB
	var err error
	if databaseURL != "" {
		db, err = sql.Open("postgres", databaseURL)
		if err != nil {
			log.Printf("database disabled: %v", err)
		}
	}

	mux := http.NewServeMux()
	mux.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		log.Printf("request method=%s path=%s remote=%s", r.Method, r.URL.Path, r.RemoteAddr)
		writeJSON(w, http.StatusOK, map[string]string{
			"status":  "ok",
			"service": "orders-service",
		})
	})

	mux.HandleFunc("/orders", func(w http.ResponseWriter, r *http.Request) {
		log.Printf("request method=%s path=%s remote=%s", r.Method, r.URL.Path, r.RemoteAddr)
		switch r.Method {
		case http.MethodGet:
			handleListOrders(w, db)
		case http.MethodPost:
			handleCreateOrder(w, r, db, catalogURL)
		default:
			w.WriteHeader(http.StatusMethodNotAllowed)
		}
	})

	log.Printf("orders-service listening on :%s", port)
	if err := http.ListenAndServe(":"+port, mux); err != nil {
		log.Fatal(err)
	}
}

func handleListOrders(w http.ResponseWriter, db *sql.DB) {
	if db == nil {
		log.Printf("list_orders source=fallback reason=no_database")
		writeJSON(w, http.StatusOK, []order{
			{ID: 1, ProductID: 1, Customer: "alice", Status: "created"},
		})
		return
	}

	log.Printf("list_orders source=database")
	rows, err := db.Query("SELECT id, product_id, customer, status FROM orders ORDER BY id")
	if err != nil {
		log.Printf("list_orders error=%v", err)
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer rows.Close()

	var orders []order
	for rows.Next() {
		var o order
		if err := rows.Scan(&o.ID, &o.ProductID, &o.Customer, &o.Status); err != nil {
			log.Printf("list_orders scan_error=%v", err)
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		orders = append(orders, o)
	}

	log.Printf("list_orders result_count=%d", len(orders))
	writeJSON(w, http.StatusOK, orders)
}

func handleCreateOrder(w http.ResponseWriter, r *http.Request, db *sql.DB, catalogURL string) {
	var input order
	if err := json.NewDecoder(r.Body).Decode(&input); err != nil {
		log.Printf("create_order invalid_json error=%v", err)
		http.Error(w, "invalid json", http.StatusBadRequest)
		return
	}

	if input.ProductID == 0 || input.Customer == "" {
		log.Printf("create_order validation_error=missing_fields product_id=%d customer=%q", input.ProductID, input.Customer)
		http.Error(w, "product_id and customer are required", http.StatusBadRequest)
		return
	}

	log.Printf("create_order validating_product product_id=%d customer=%q", input.ProductID, input.Customer)
	if err := validateProduct(input.ProductID, catalogURL); err != nil {
		log.Printf("create_order validation_error=%v", err)
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	input.Status = "created"

	if db == nil {
		input.ID = 99
		log.Printf("create_order source=fallback assigned_id=%d product_id=%d customer=%q", input.ID, input.ProductID, input.Customer)
		writeJSON(w, http.StatusCreated, input)
		return
	}

	err := db.QueryRow(
		"INSERT INTO orders (product_id, customer, status) VALUES ($1, $2, $3) RETURNING id",
		input.ProductID,
		input.Customer,
		input.Status,
	).Scan(&input.ID)
	if err != nil {
		log.Printf("create_order db_error=%v", err)
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	log.Printf("create_order created id=%d product_id=%d customer=%q status=%q", input.ID, input.ProductID, input.Customer, input.Status)
	writeJSON(w, http.StatusCreated, input)
}

func validateProduct(productID int, catalogURL string) error {
	client := http.Client{Timeout: 2 * time.Second}

	log.Printf("catalog_validation request_url=%s/products product_id=%d", catalogURL, productID)
	resp, err := client.Get(fmt.Sprintf("%s/products", catalogURL))
	if err != nil {
		return fmt.Errorf("catalog-service unavailable: %w", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		return fmt.Errorf("catalog-service returned status %d", resp.StatusCode)
	}

	var products []product
	if err := json.NewDecoder(resp.Body).Decode(&products); err != nil {
		return fmt.Errorf("invalid response from catalog-service")
	}

	for _, product := range products {
		if product.ID == productID {
			log.Printf("catalog_validation success product_id=%d product_name=%q", product.ID, product.Name)
			return nil
		}
	}

	log.Printf("catalog_validation unknown_product product_id=%d", productID)
	return fmt.Errorf("unknown product_id %d", productID)
}

func writeJSON(w http.ResponseWriter, status int, payload any) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	_ = json.NewEncoder(w).Encode(payload)
}

func getEnv(key, fallback string) string {
	value := os.Getenv(key)
	if value == "" {
		return fallback
	}
	return value
}
