# Module 3 Examples

These examples focus on internal traffic and service discovery.

- `whoami-deployment.yaml`: multiple instructor demo replicas.
- `whoami-service.yaml`: stable entry point for the `whoami` pods.
- `whoami-ingress.yaml`: ingress rule that routes `whoami.demo.local` to the `whoami` service.

The application services and application ingress manifests for `catalog-service`, `orders-service`, `frontend`, and `postgres` are exercise solutions and belong in `solutions/`, not in this published `examples/` directory.

## Suggested Commands

```bash
kubectl apply -f module-3/examples/whoami-deployment.yaml
```

Creates multiple backend pods for the networking demo.

```bash
kubectl apply -f module-3/examples/whoami-service.yaml
```

Creates a stable virtual IP and DNS name that target the `whoami` pods.

```bash
kubectl get svc,endpoints -n demo-app
```

Shows the created `Service` and the backend pod IPs selected behind it.

```bash
kubectl port-forward -n demo-app service/whoami 8080:8080
```

Lets students call the `Service` locally and observe load balancing across replicas.

```bash
kubectl apply -f module-3/examples/whoami-ingress.yaml
```

Creates the HTTP routing rule consumed by the cluster ingress controller.
