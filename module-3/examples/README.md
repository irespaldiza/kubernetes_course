# Module 3 Examples

These examples focus on internal traffic and service discovery.

- `whoami-deployment.yaml`: multiple instructor demo replicas.
- `whoami-service.yaml`: stable entry point for the `whoami` pods.
- `whoami-ingress.yaml`: ingress rule that routes `whoami.demo.local` to the `whoami` service.

The application services and application ingress manifests for `catalog-service`, `orders-service`, and `frontend` are exercise solutions and belong in `solutions/`, not in this published `examples/` directory.
