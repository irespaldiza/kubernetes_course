# Module 7 Exercises

This module has three student exercises, all focused on Helm.

Kustomize, Grafana, and Kompose are examples for class explanation and comparison.

Reference answers live in `solutions/helm/external-charts/` and `solutions/helm/demo-app/`.

## 1. Install Helm

Goal:
prepare the local environment and confirm the Helm client is ready.

## Suggested Commands

```bash
helm version
```

Shows whether Helm is installed and prints the client version.

```bash
helm repo list
```

Confirms the client is available and shows the currently configured repositories.

## Tasks

1. Install Helm on your machine.
2. Run `helm version`.
3. Explain what Helm is used for in Kubernetes.

## 2. Deploy the WordPress Chart

Goal:
learn the first practical Helm workflow without writing templates, first with `--set` and then with a custom config file.

## Suggested Commands

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

Adds the chart repository used in the exercise.

```bash
helm repo update
```

Refreshes repository metadata before rendering or installing the chart.

```bash
helm show values bitnami/wordpress
```

Shows the upstream chart defaults so students can discover which values to override.

```bash
helm template blog bitnami/wordpress --set service.type=ClusterIP
```

Renders the chart locally with a quick one-off override from the command line.

```bash
helm template blog bitnami/wordpress -f ./config.yaml
```

Renders the chart locally using the student config file.

```bash
helm install blog bitnami/wordpress -n blog --create-namespace -f ./config.yaml
```

Installs the chart into a dedicated namespace using the same config file.

## Tasks

1. Run `helm show values bitnami/wordpress` and identify which keys must be overridden.
2. Render the chart once with a simple `--set` override to confirm that Helm can change values from the command line.
3. Create a file named `config.yaml`.
4. In `config.yaml`, set a blog name, admin user, and email.
5. In `config.yaml`, keep the service internal with `ClusterIP`.
6. In `config.yaml`, enable persistence for both WordPress and MariaDB.
7. Add at least one extra change that is not shown in class, so you have to inspect the chart values yourself.
8. Render the chart locally with `helm template`.
9. Deploy the chart into the cluster.

## 3. Create a Chart for `demo-app`

Goal:
package the course application with Helm templates and values.

## Suggested Commands

```bash
helm create demo-app
```

Generates a starter chart structure that students can simplify or replace.

```bash
helm template demo-app ./demo-app
```

Renders the in-progress chart locally to inspect the generated manifests.

```bash
helm lint ./demo-app
```

Checks the chart structure and common template issues.

## Tasks

1. Create a chart that deploys `frontend`, `catalog-service`, `orders-service`, and `postgres`.
2. Move image names, tags, replica counts, ports, and database values into `values.yaml`.
3. Include the frontend Nginx configuration as a templated `ConfigMap`.
4. Include the shared database `Secret`.
5. Render the chart locally and review the generated manifests.
6. Explain which parts are easier with Helm than with plain manifests or Kustomize.
