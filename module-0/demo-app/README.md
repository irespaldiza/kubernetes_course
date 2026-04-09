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

## Suggested Commands

```bash
docker build -t module0/catalog-service:local module-0/demo-app/catalog-service
```

Builds the Python catalog service image used by the course manifests.

```bash
docker build -t module0/orders-service-go:local module-0/demo-app/orders-service
```

Builds the Go implementation of the orders service.

```bash
docker build -t module0/orders-service-java:local module-0/demo-app/orders-service-java
```

Builds the Java implementation of the same orders service contract.

```bash
docker build -t module0/frontend:local module-0/demo-app/frontend
```

Builds the frontend image served by Nginx.

```bash
docker compose -f module-0/demo-app/docker-compose.go.yml up --build
```

Builds any missing images and starts the local stack with the Go orders service implementation.

```bash
docker compose -f module-0/demo-app/docker-compose.java.yml up --build
```

Builds any missing images and starts the local stack with the Java orders service implementation.

```bash
docker compose -f module-0/demo-app/docker-compose.go.yml down -v
```

Stops the Go-based stack and removes the PostgreSQL named volume so the database starts clean on the next run.

```bash
curl http://127.0.0.1:8080
```

Checks that the frontend is reachable on the published local port.

```bash
curl http://127.0.0.1:8080/api/catalog/products
```

Tests the frontend proxy path for the catalog API.

```bash
curl http://127.0.0.1:8080/api/orders/orders
```

Tests the frontend proxy path for the orders API.

## Local Compose Workflows

Use the Go-based stack:

```bash
docker compose -f module-0/demo-app/docker-compose.go.yml up --build
```

Use the Java-based stack:

```bash
docker compose -f module-0/demo-app/docker-compose.java.yml up --build
```
