# Demo Application

This project is the working application used across the course.

## Project Structure

- `frontend/`: web client served by Nginx.
- `catalog-service/`: Python API for catalog data.
- `orders-service/`: Go API for orders.
- `orders-service-java/`: Java implementation of the same orders API contract.
- `infra/postgres/`: database initialization script.
- `docker-compose.yml`: default local stack.
- `docker-compose.go.yml`: local stack with the Go implementation of `orders-service`.
- `docker-compose.java.yml`: local stack with the Java implementation of `orders-service`.

## Image Contract

The course expects these local image tags:

- `module0/catalog-service:local`
- `module0/orders-service-go:local`
- `module0/orders-service-java:local`
- `module0/frontend:local`

## Local Compose Workflows

Run the Go-based stack:

```bash
docker compose -f docker-compose.go.yml up --build
```

Run the Java-based stack:

```bash
docker compose -f docker-compose.java.yml up --build
```
