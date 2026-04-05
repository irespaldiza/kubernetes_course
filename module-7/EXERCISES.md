# Module 7 Exercises

Use `examples/helm/whoami-python/` as the reference chart and build the real exercise around `demo-app`.
The reference answer lives in `solutions/helm/demo-app/`.

## 1. Read the Reference Chart

Goal:
understand the Helm patterns before applying them to the course application.

Tasks:

1. Open `examples/helm/whoami-python/Chart.yaml`.
2. Open `examples/helm/whoami-python/values.yaml`.
3. Open the templates directory.
4. Explain the role of each template.

## 2. Create a Chart for `demo-app`

Goal:
package the application the students have been building across the course.

Tasks:

1. Create a chart that deploys `frontend`, `catalog-service`, `orders-service`, and `postgres`.
2. Move image names, tags, replica counts, ports, and database values into `values.yaml`.
3. Include the frontend Nginx configuration as a templated `ConfigMap`.
4. Include the shared database `Secret`.

## 3. Add Optional Features to the Chart

Goal:
reuse patterns from the reference chart where they add value.

Tasks:

1. Add at least one Helm test.
2. Add helper templates for names and labels.
3. Decide whether to include an optional `initContainer` pattern and justify it.
4. Render the chart with `helm template`.

## 4. Verify the `demo-app` Chart

Goal:
confirm that the chart is not only syntactically correct but operationally coherent.

Tasks:

1. Install the chart in a test namespace.
2. Run `helm test`.
3. Confirm that the main services can start with the rendered values.
4. Compare your chart with `solutions/helm/demo-app/` and identify the main differences.
