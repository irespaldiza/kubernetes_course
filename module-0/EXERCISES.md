# Module 0 Exercises

These exercises use the demo project in [demo-app/README.md](/Users/irespaldiza/Documents/cursos/kubernetes/git/microservices/module-0/demo-app/README.md).

## 1. Install and Verify Docker

Goal:
make sure the local environment is ready before building any course image.

Tasks:

1. Install Docker Desktop or another compatible local Docker runtime if it is not already installed.
2. Confirm that the Docker daemon is running.
3. Run `docker version`.
4. Run `docker info`.
5. Confirm that `docker compose` is available.

## 2. Build the `orders-service` Image

Goal:
build a local Docker image for an application different from the instructor reference app.

Tasks:

1. Review `demo-app/orders-service/cmd/orders-service/main.go`.
2. Build the image with the tag `module0/orders-service-go:local`.
3. Run it with `PORT` and `DATABASE_URL`.
4. Test `GET /health` and `GET /orders`.

## 3. Swap the Same API to Java

Goal:
show that the API contract stays stable while the implementation changes.

Tasks:

1. Review `demo-app/orders-service-java/`.
2. Build the image with the tag `module0/orders-service-java:local`.
3. Start the stack with `docker compose -f demo-app/docker-compose.java.yml up --build`.
4. Verify that the frontend still works without any change.

## 4. Build the `frontend` Image

Goal:
repeat the process with a different stack and runtime.

Tasks:

1. Review `demo-app/frontend/Dockerfile`.
2. Build the image with the tag `module0/frontend:local`.
3. Run it locally.
4. Verify that Nginx serves the generated content.

## 5. Run the Full Stack with `docker compose`

Goal:
understand how the application components work together in local Docker.

Tasks:

1. Choose either `demo-app/docker-compose.go.yml` or `demo-app/docker-compose.java.yml`.
2. Start the stack with `docker compose -f <file> up --build`.
3. Open the frontend.
4. Test the catalog and orders flows end to end.

## 6. Document the Course Image Contract

Goal:
make the required name and tag for each local build explicit.

Tasks:

1. Write down the expected tag for each application.
2. Write down which environment variables each one needs.
3. Identify which values should go to `ConfigMap` and which should go to `Secret`.
