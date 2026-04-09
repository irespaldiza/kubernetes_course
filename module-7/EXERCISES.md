# Module 7 Exercises

Use `examples/helm/whoami-python/` as the reference chart and build the real exercise around `demo-app`.
The reference answer lives in `solutions/helm/demo-app/`.

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

## 2. Create a Chart for `demo-app`

Goal:
package the application the students have been building across the course.

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

## 3. Add Optional Features to the Chart

Goal:
reuse patterns from the reference chart where they add value.

## Suggested Commands

```bash
helm lint ./demo-app
```

Checks the chart after helpers, tests, and optional features are added.

```bash
helm template demo-app ./demo-app
```

Renders the final chart output for review before installation.

## Tasks

1. Add at least one Helm test.
2. Add helper templates for names and labels.
3. Decide whether to include an optional `initContainer` pattern and justify it.
4. Render the chart with `helm template`.

## 4. Verify the `demo-app` Chart

Goal:
confirm that the chart is not only syntactically correct but operationally coherent.

## Suggested Commands

```bash
helm install demo-app ./demo-app -n demo-app-test --create-namespace
```

Installs the chart into a separate test namespace.

```bash
helm test demo-app -n demo-app-test
```

Runs the chart test hooks after installation.

```bash
helm uninstall demo-app -n demo-app-test
```

Removes the test release after verification.

## Tasks

1. Install the chart in a test namespace.
2. Run `helm test`.
3. Confirm that the main services can start with the rendered values.
4. Compare your chart with `solutions/helm/demo-app/` and identify the main differences.
