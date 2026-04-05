# Kubernetes Course

Working repository for a Kubernetes course.

The repository currently includes material for:

- `module-0`: microservices and Docker foundations.
- `module-1`: Kubernetes architecture and the declarative model.
- `module-2`: running applications with pods and deployments.
- `module-3`: networking, services, ingress, and Gateway API.
- `module-4`: configuration and secrets.
- `module-5`: persistence and stateful workloads.
- `module-6`: observability with logs, probes, and metrics.
- `module-7`: CI/CD, Kustomize, Helm, Kompose, and GitOps.

## Structure

- `module-0/README.md`: module guide and teaching notes.
- `module-1/README.md`: module guide and teaching notes.
- `module-2/README.md` to `module-7/README.md`: module guides and teaching notes.
- `module-0/EXERCISES.md` to `module-7/EXERCISES.md`: student exercise sets.
- `module-0/demo-app/`: course demo with multiple applications.
- `module-1/examples/` to `module-7/examples/`: manifests and delivery examples for each module.

## Demo Application

The demo is organized by application:

- `module-0/demo-app/frontend/`: React + Nginx.
- `module-0/demo-app/catalog-service/`: Python + FastAPI.
- `module-0/demo-app/orders-service/`: Go + HTTP API.
- `module-0/demo-app/orders-service-java/`: Java implementation of the same API contract.
- `module-0/demo-app/infra/postgres/`: database initialization.
- `module-0/demo-app/docker-compose.go.yml`: local stack with the Go orders service.
- `module-0/demo-app/docker-compose.java.yml`: local stack with the Java orders service.

Additional small example applications also appear in later modules when needed for focused topics such as configuration, observability, or chart design.
