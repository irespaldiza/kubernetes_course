import os
from typing import Any

from fastapi import FastAPI
import psycopg


app = FastAPI(title="catalog-service")


PRODUCTS_FALLBACK = [
    {"id": 1, "name": "Kubernetes Mug", "price": 12.5},
    {"id": 2, "name": "Docker Sticker Pack", "price": 4.0},
    {"id": 3, "name": "Cloud Native T-Shirt", "price": 19.9},
]


def get_connection() -> psycopg.Connection[Any]:
    database_url = os.getenv("DATABASE_URL")
    if not database_url:
        raise RuntimeError("DATABASE_URL is required")
    return psycopg.connect(database_url)


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok", "service": "catalog-service"}


@app.get("/products")
def list_products() -> list[dict[str, Any]]:
    try:
        with get_connection() as conn:
            with conn.cursor() as cur:
                cur.execute(
                    "SELECT id, name, price FROM products ORDER BY id"
                )
                rows = cur.fetchall()
                return [
                    {"id": row[0], "name": row[1], "price": float(row[2])}
                    for row in rows
                ]
    except Exception:
        # Useful fallback in class when the database is not ready yet.
        return PRODUCTS_FALLBACK
