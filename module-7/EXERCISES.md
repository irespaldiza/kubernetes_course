# Module 7 Exercises

This module has three student exercises, all focused on Helm.

Kustomize, Grafana, and Kompose are examples for class explanation and comparison.

Reference answers live in `solutions/helm/external-charts/`, `solutions/helm/demo-app/`, and `solutions/helm/demo-app-bitnami-postgresql/`.

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

Shows the default values of the chart directly in the terminal.

Use this first to understand the structure of the chart and the names of the keys you can override.

```bash
helm show values bitnami/wordpress > values.yaml
```

Saves the default chart values into a local file so you can inspect them more comfortably.

This is useful when the file is large and you want to search for sections such as `wordpressUsername`, `wordpressBlogName`, `service`, `persistence`, or `mariadb`.

The idea is not to edit this downloaded file directly as your final config, but to use it as a reference to discover what the chart supports and then create a smaller `config.yaml` with only the values you want to override.

Suggested workflow:

1. Run `helm show values bitnami/wordpress`.
2. If the output is too large, save it with `helm show values bitnami/wordpress > values.yaml`.
3. Search inside that file for the settings you need to change.
4. Copy only those keys into your own `config.yaml`.

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
2. If needed, export the defaults with `helm show values bitnami/wordpress > values.yaml` and use that file as a reference to find the exact keys you need.
3. Render the chart once with a simple `--set` override to confirm that Helm can change values from the command line.
4. Create a file named `config.yaml`.
5. In `config.yaml`, set a blog name, admin user, and email.
6. In `config.yaml`, keep the service internal with `ClusterIP`.
7. In `config.yaml`, enable persistence for both WordPress and MariaDB.
8. Add at least one extra change that is not shown in class, so you have to inspect the chart values yourself.
9. Render the chart locally with `helm template`.
10. Deploy the chart into the cluster.

## 3. Create a Chart for `demo-app`

Goal:
package the course application with Helm templates and values.

Students do not need prior Go template knowledge for this exercise.
The chart can be completed with simple substitutions from `values.yaml`.
Use `module-7/HELM_GO_TEMPLATE.md` as a step-by-step support guide.

## Suggested Commands

```bash
cp -R module-7/examples/helm/demo-app-starter ./demo-app
```

Copies a common starter chart so all students begin from the same manifests.

```bash
helm template demo-app ./demo-app
```

Renders the in-progress chart locally to inspect the generated manifests.

```bash
helm lint ./demo-app
```

Checks the chart structure and common template issues.

## Tasks

1. Copy `module-7/examples/helm/demo-app-starter/` into your workspace as the starting point for the exercise.
2. Use that starter chart to deploy `frontend`, `catalog-service`, `orders-service`, and `postgres`.
3. Move the basic configuration into `values.yaml` instead of leaving it hardcoded in the templates.
4. At minimum, make image names and tags configurable.
5. At minimum, make replica counts configurable.
6. At minimum, make service ports and container ports configurable.
7. At minimum, make database secret values configurable.
8. At minimum, make the frontend Nginx `ConfigMap` configurable.
9. At minimum, make the Postgres init SQL `ConfigMap` configurable.
10. At minimum, make the Postgres persistence size configurable.
11. Keep the chart intentionally simple. You do not need to add advanced helpers, conditionals, autoscaling, probes, or chart dependencies.
12. Render the chart locally and review the generated manifests.
13. Explain which parts are easier with Helm than with plain manifests or Kustomize.

Recommended implementation order:

1. Replace one replica count with a value from `values.yaml`.
2. Replace one image string with a value from `values.yaml`.
3. Replace one port with a value from `values.yaml`.
4. Repeat the same pattern for the other services.
5. Move the database secret values into `values.yaml`.
6. Move the Nginx config into `values.yaml`.
7. Move the Postgres init SQL into `values.yaml`.
