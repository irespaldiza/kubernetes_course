import React, { useEffect, useState } from "react";
import ReactDOM from "react-dom/client";
import "./styles.css";

function normalizeProducts(payload) {
  if (!Array.isArray(payload)) {
    return [];
  }

  return payload.map((product) => ({
    id: product.id,
    name: product.name,
    price: Number(product.price ?? 0),
  }));
}

function normalizeOrders(payload) {
  if (!Array.isArray(payload)) {
    return [];
  }

  return payload.map((order) => ({
    id: order.id,
    product_id: order.product_id,
    customer: order.customer,
    status: order.status,
  }));
}

function getErrorMessage(error, fallback) {
  if (error instanceof Error && error.message) {
    return error.message;
  }

  return fallback;
}

function App() {
  const [products, setProducts] = useState([]);
  const [orders, setOrders] = useState([]);
  const [customer, setCustomer] = useState("");
  const [productId, setProductId] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    async function loadData() {
      try {
        const [productsResponse, ordersResponse] = await Promise.all([
          fetch("/api/catalog/products"),
          fetch("/api/orders/orders"),
        ]);

        if (!productsResponse.ok || !ordersResponse.ok) {
          throw new Error("Could not load application data");
        }

        const [productsData, ordersData] = await Promise.all([
          productsResponse.json(),
          ordersResponse.json(),
        ]);

        setProducts(normalizeProducts(productsData));
        setOrders(normalizeOrders(ordersData));
        setError("");
      } catch (loadError) {
        setError(getErrorMessage(loadError, "Could not load application data"));
      } finally {
        setLoading(false);
      }
    }

    loadData();
  }, []);

  async function handleSubmit(event) {
    event.preventDefault();
    setSubmitting(true);
    setError("");

    try {
      const response = await fetch("/api/orders/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          product_id: Number(productId),
          customer,
        }),
      });

      if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Could not create order");
      }

      const newOrder = await response.json();
      setOrders((currentOrders) => [...currentOrders, ...normalizeOrders([newOrder])]);
      setCustomer("");
      setProductId("");
    } catch (submitError) {
      setError(getErrorMessage(submitError, "Could not create order"));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <main className="app">
      <section className="panel">
        <span className="eyebrow">Module 0</span>
        <h1>Microservices Demo</h1>
        <p>
          The frontend now talks to the real APIs. Creating an order goes
          through <code>orders-service</code>, which validates the product
          against <code>catalog-service</code> before writing to the database.
        </p>

        {error ? <p className="error">{error}</p> : null}

        <div className="grid">
          <article className="card">
            <h2>Catalog Service</h2>
            <p>
              Endpoint: <code>GET /products</code>
            </p>
            {loading ? (
              <p>Loading products...</p>
            ) : (
              <ul className="list">
                {products.map((product) => (
                  <li key={product.id}>
                    <strong>{product.name}</strong>
                    <span>{Number(product.price).toFixed(2)} EUR</span>
                  </li>
                ))}
              </ul>
            )}
          </article>

          <article className="card">
            <h2>Orders Service</h2>
            <p>
              Endpoints: <code>GET /orders</code> and <code>POST /orders</code>
            </p>
            <form className="form" onSubmit={handleSubmit}>
              <label>
                Customer
                <input
                  value={customer}
                  onChange={(event) => setCustomer(event.target.value)}
                  placeholder="alice"
                  required
                />
              </label>
              <label>
                Product
                <select
                  value={productId}
                  onChange={(event) => setProductId(event.target.value)}
                  required
                >
                  <option value="">Select a product</option>
                  {products.map((product) => (
                    <option key={product.id} value={product.id}>
                      {product.name}
                    </option>
                  ))}
                </select>
              </label>
              <button type="submit" disabled={submitting || loading}>
                {submitting ? "Creating..." : "Create order"}
              </button>
            </form>
            {loading ? (
              <p>Loading orders...</p>
            ) : (
              <ul className="list">
                {orders.map((order) => (
                  <li key={order.id}>
                    <strong>#{order.id}</strong>
                    <span>
                      product {order.product_id} · {order.customer} · {order.status}
                    </span>
                  </li>
                ))}
              </ul>
            )}
          </article>
        </div>
      </section>
    </main>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
