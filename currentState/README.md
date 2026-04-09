# Current State

This folder contains the manifests needed to recreate the shared cluster state used during the course.

It is intended as a quick recovery point for students so they can continue from the same baseline without rebuilding every resource by hand.

## Included Resources

- `namespace.yaml`
- `postgres-deployment.yaml`
- `postgres-service.yaml`
- `catalog-deployment.yaml`
- `catalog-service.yaml`
- `orders-deployment.yaml`
- `orders-service.yaml`
- `frontend-deployment.yaml`
- `frontend-service.yaml`
- `frontend-ingress.yaml`

## Apply the Baseline

Before applying these manifests, make sure your cluster already has an ingress controller installed.

If needed, follow the guide in [`module-3/installIngress.md`](../module-3/installIngress.md).

Apply the manifests in a predictable order:

```bash
kubectl apply -f currentState/namespace.yaml
kubectl apply -f currentState/postgres-deployment.yaml -f currentState/postgres-service.yaml
kubectl apply -f currentState/catalog-deployment.yaml -f currentState/catalog-service.yaml
kubectl apply -f currentState/orders-deployment.yaml -f currentState/orders-service.yaml
kubectl apply -f currentState/frontend-deployment.yaml -f currentState/frontend-service.yaml -f currentState/frontend-ingress.yaml
```

You can also apply the whole folder in one step:

```bash
kubectl apply -f currentState/
```

## Assumptions

- the local images already exist:
  - `module0/catalog-service:local`
  - `module0/orders-service-go:local`
  - `module0/frontend:local`
- the cluster has an ingress controller available for `frontend-ingress.yaml`; if not, install it first using [`module-3/installIngress.md`](../module-3/installIngress.md)
- the host `frontend.demo.local` resolves to the ingress entrypoint in the student environment
