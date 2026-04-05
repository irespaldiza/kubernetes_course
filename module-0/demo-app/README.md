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

## Why This Project Works for Class

- It is small enough to explain quickly.
- It has clear boundaries between frontend, APIs, and data.
- It makes service dependencies visible.
- It supports Docker and Docker Compose without changing the domain.
- It lets the course reuse the same images across multiple modules.

## Course Usage

- `catalog-service` is the smallest backend entry point for packaging and runtime demos.
- `orders-service` and `orders-service-java` are useful for comparing implementations behind the same API contract.
- `frontend` makes the system behavior visible end to end.

This makes two points visible:

- containers abstract the runtime;
- the runnable unit is the image, not the source folder or the programming language.

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
