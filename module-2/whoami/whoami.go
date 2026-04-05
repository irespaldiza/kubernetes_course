package main

import (
	"fmt"
	"log"
	"net/http"
	"os"
)

func main() {
	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}
	myEnvVar := os.Getenv("MY_ENV_VAR")
	secretMessage := os.Getenv("SECRET_MESSAGE")

	fmt.Fprintf(os.Stdout, "Listening on :%s\n", port)
	hostname, err := os.Hostname()
	if err != nil {
		hostname = "unknown"
	}
	http.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		fmt.Fprintln(w, "ok")
	})
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprintf(w, "This is the pod %s, MY_ENV_VAR: %s, SECRET_MESSAGE: %s\n", hostname, myEnvVar, secretMessage)
	})

	log.Fatal(http.ListenAndServe(":"+port, nil))
}
