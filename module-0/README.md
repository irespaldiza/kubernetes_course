# Module 0: Microservices and Docker

## Module Goal

The goal is for them to understand:

- why microservices appear;
- which problems they solve and which problems they introduce;
- how to package an application with Docker;
- how to build images locally;
- how to run a distributed application locally;
- how to keep those application images consistent across local workflows.

## Module Assets

- `demo-app/`: course demo project used across later modules.
- `demo-app/README.md`: project documentation and image contract.
- `EXERCISES.md`: student exercises for this module.

## Recommended Teaching Narrative

### Block 1: From Monolith to Microservices

Example story:

- An online shop starts as a single application.
- Over time, catalog, orders, payments, and notifications appear as distinct modules.
- Deploying everything together becomes slow and risky.
- Each team wants to evolve at a different pace.

Key points:

- independent deployment;
- fault isolation;
- per-component scaling;
- team autonomy;
- operational cost and distributed complexity.

### Block 2: Docker as the Runtime Packaging Layer

Example story:

- "It works on my machine" is no longer acceptable.
- We package code, runtime, and dependencies in an image.
- The container makes the environment repeatable.

Key points:

- image versus container;
- `Dockerfile`;
- ports;
- environment variables;
- networks;
- volumes;
- `docker compose` as local support.

### Block 3: Moving from One Container to a Multi-Service System

Map each local concept to a larger system view:

- one container -> one service process;
- multiple containers -> one composed application;
- environment variables -> per-environment configuration;
- volumes -> data that outlives one process;
- published ports -> explicit entry points between components.

## In-Class Demos

### Demo 1: Minimal HTTP Service

Goal:
explain that a microservice is just a process with a clear API.

Steps:

1. Start a Python API with `/health` and `/products`.
2. Run it locally.
3. Put it into a container.
4. Run it with `docker run`.

Key messages:

- The service does not need to know the host.
- Configuration should live outside the code.
- The container should be small and reproducible.

### Demo 2: Build One Application and Run It Locally

Goal:
show that one concrete application in the system can be packaged and executed independently.

Steps:

1. Build the local image for `catalog-service`.
2. Run it locally with the required environment.
3. Test `GET /health` and `GET /products`.
4. Explain why the same image can be reused consistently across local environments.

Key messages:

- The image is the portable unit that survives beyond local Docker.
- The runtime target can change later without rebuilding the application logic.

### Demo 3: Student-Built Local Images

Goal:
move from watching to building.

Steps:

1. Give students `orders-service` and `frontend` as separate applications.
2. Ask them to build their images locally.
3. Reuse the same tags that the instructor prepares in advance.
4. Explain that the lab can continue even if they do not finish building in time.

### Demo 4: The API Contract Survives a Rewrite

Goal:
show that `orders-service` can be replaced without changing the frontend or the rest of the system.

Steps:

1. Start the stack with `docker-compose.go.yml`.
2. Show the behavior of the system through the frontend.
3. Stop it and start the stack with `docker-compose.java.yml`.
4. Show that the frontend still works because the HTTP contract is unchanged.

## Recommended Class Flow

1. Explain the domain in 5 minutes.
2. Show the structure with multiple applications.
3. Build `catalog-service` live.
4. Show its `Dockerfile`.
5. Run `catalog-service` locally.
6. Hand `orders-service`, `orders-service-java`, and `frontend` to students so they build the images locally.
7. Use `docker compose` as a comparison or support tool, not as the main narrative.

## Important Teaching Constraint

Do not try to make module 0 into "real production microservices".
It needs to stay small, repeatable, and useful as preparation for the rest of the course.

Avoid in this module:

- complex authentication;
- mandatory queues;
- tracing;
- service mesh;
- CI/CD;
- too much business logic.

## Practical Rule for Class

Use stable local tags, for example:

- `module0/catalog-service:local`
- `module0/orders-service-go:local`
- `module0/frontend:local`

If you already have those images built in advance, the class still works even if a student does not finish their build.
