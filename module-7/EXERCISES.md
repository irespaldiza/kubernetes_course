# Module 7 Exercises

Use `examples/helm/whoami-python/` as the reference chart and build the real exercise around `demo-app`.
Reference answers live in `solutions/manifests/demo-app/`, `solutions/kustomize/demo-app/`, and `solutions/helm/demo-app/`.

## 1. Read the Reference Chart

Goal:
understand the Helm patterns before applying them to the course application.

## Suggested Commands

```bash
helm lint module-7/examples/helm/whoami-python
```

Validates the example chart structure and common template issues.

```bash
helm template whoami-python module-7/examples/helm/whoami-python
```

Renders the chart locally so students can inspect the generated Kubernetes manifests.

## Tasks

1. Open `examples/helm/whoami-python/Chart.yaml`.
2. Open `examples/helm/whoami-python/values.yaml`.
3. Open the templates directory.
4. Explain the role of each template.

## 2. Gather the Full Application as Plain Manifests

Goal:
assemble the full application without an additional packaging tool.

## Suggested Commands

```bash
kubectl apply --dry-run=client -f module-7/solutions/manifests/demo-app/all.yaml
```

Validates the full manifest bundle locally without creating resources.

```bash
kubectl apply -f module-7/solutions/manifests/demo-app/all.yaml
```

Applies the gathered manifest bundle to the cluster.

## Tasks

1. Create one manifest bundle that deploys `frontend`, `catalog-service`, `orders-service`, and `postgres`.
2. Include the frontend Nginx `ConfigMap`.
3. Include the shared database `Secret`.
4. Include the PostgreSQL PVC.

## 3. Package the Same Application with Kustomize

Goal:
organize the same resources with a lightweight manifest packaging layer.

## Suggested Commands

```bash
kubectl kustomize module-7/solutions/kustomize/demo-app
```

Renders the Kustomize output locally for inspection.

```bash
kubectl apply -k module-7/solutions/kustomize/demo-app
```

Applies the Kustomize package to the cluster.

## Tasks

1. Create a `kustomization.yaml` that references the full application resources.
2. Keep the same application components and supporting resources as in the plain-manifest version.
3. Render the package with `kubectl kustomize`.
4. Explain what Kustomize improves and what it does not abstract.

## 4. Create a Chart for `demo-app`

Goal:
package the same application with Helm templates and values.

## Suggested Commands

```bash
helm create demo-app
```

Generates a starter chart structure that students can simplify or replace.

```bash
helm template demo-app ./demo-app
```

Renders the in-progress chart locally to inspect the generated manifests.

## Tasks

1. Create a chart that deploys `frontend`, `catalog-service`, `orders-service`, and `postgres`.
2. Move image names, tags, replica counts, ports, and database values into `values.yaml`.
3. Include the frontend Nginx configuration as a templated `ConfigMap`.
4. Include the shared database `Secret`.

## 5. Compare and Verify the Three Approaches

Goal:
compare plain manifests, Kustomize, and Helm using the same application.

## Suggested Commands

```bash
helm lint ./demo-app
```

Checks the chart after helpers and values are added.

```bash
helm install demo-app ./demo-app -n demo-app-test --create-namespace
```

Installs the chart into a separate test namespace.

```bash
kubectl apply -k module-7/solutions/kustomize/demo-app
```

Applies the Kustomize package for side-by-side comparison in the same cluster or another namespace.

```bash
helm uninstall demo-app -n demo-app-test
```

Removes the Helm test release after verification.

## Tasks

1. Render and review the plain manifest bundle.
2. Render and review the Kustomize package.
3. Render and review the Helm chart.
4. Compare your outputs with the three reference solutions and identify the main differences.
