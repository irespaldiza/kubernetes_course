# Demo App Helm Chart

Reference chart for deploying the course `demo-app` with Helm.

## What It Creates

This chart creates:

- `frontend`
- `catalog-service`
- `orders-service`
- `postgres`
- a `Secret` for database settings
- a `ConfigMap` for the frontend Nginx configuration
- a `ConfigMap` with the Postgres `init.sql`
- an optional `Ingress` for the frontend

Resource names depend on the Helm release name. For example, if the release name is `demo-app`, the catalog service name will be `demo-app-catalog-service`.

## Basic Commands

Lint the chart:

```bash
helm lint module-7/solutions/helm/demo-app
```

Render the manifests locally without installing them:

```bash
helm template demo-app module-7/solutions/helm/demo-app -n demo-app
```

Install the chart:

```bash
helm install demo-app module-7/solutions/helm/demo-app -n demo-app --create-namespace
```

Upgrade an existing release:

```bash
helm upgrade demo-app module-7/solutions/helm/demo-app -n demo-app
```

Uninstall the release:

```bash
helm uninstall demo-app -n demo-app
```

## Important Values

The most common changes are in `values.yaml`:

- `frontend.image.*`
- `catalogService.image.*`
- `ordersService.image.*`
- `postgres.image.*`
- `frontend.replicaCount`
- `catalogService.replicaCount`
- `ordersService.replicaCount`
- `postgres.persistence.size`
- `ingress.enabled`
- `ingress.className`
- `ingress.host`

## Ports

For the application services:

- `containerPort`: port where the application process listens inside the container
- `service.port`: port exposed by the Kubernetes `Service`

The `Service` forwards traffic from `service.port` to `containerPort`.

## Examples

Disable the ingress:

```bash
helm upgrade --install demo-app module-7/solutions/helm/demo-app \
  -n demo-app \
  --create-namespace \
  --set ingress.enabled=false
```

Override the application images:

```bash
helm upgrade --install demo-app module-7/solutions/helm/demo-app \
  -n demo-app \
  --create-namespace \
  --set frontend.image.repository=my-registry/frontend \
  --set frontend.image.tag=v1 \
  --set catalogService.image.repository=my-registry/catalog-service \
  --set catalogService.image.tag=v1 \
  --set ordersService.image.repository=my-registry/orders-service \
  --set ordersService.image.tag=v1
```

Use a custom values file:

```bash
helm upgrade --install demo-app module-7/solutions/helm/demo-app \
  -n demo-app \
  --create-namespace \
  -f my-values.yaml
```

## Quick Checks

List the release resources:

```bash
kubectl get all -n demo-app
```

Inspect the rendered manifest stored in Helm:

```bash
helm get manifest demo-app -n demo-app
```
