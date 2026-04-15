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
export WP_PASSWORD='student123'
export DB_PASSWORD='dbpass123'
export DB_ROOT_PASSWORD='rootpass123'
helm template blog bitnami/wordpress -f ./config.yaml \
  --set wordpressPassword="$WP_PASSWORD" \
  --set mariadb.auth.password="$DB_PASSWORD" \
  --set mariadb.auth.rootPassword="$DB_ROOT_PASSWORD"
```

Renders the chart locally using the student config file plus the passwords passed from the command line.

```bash
export WP_PASSWORD='student123'
export DB_PASSWORD='dbpass123'
export DB_ROOT_PASSWORD='rootpass123'
helm install blog bitnami/wordpress -n blog --create-namespace -f ./config.yaml \
  --set wordpressPassword="$WP_PASSWORD" \
  --set mariadb.auth.password="$DB_PASSWORD" \
  --set mariadb.auth.rootPassword="$DB_ROOT_PASSWORD"
```

Installs the chart into a dedicated namespace using the same config file and runtime passwords.

## Tasks

1. Run `helm show values bitnami/wordpress` and identify which keys must be overridden.
2. If needed, export the defaults with `helm show values bitnami/wordpress > values.yaml` and use that file as a reference to find the exact keys you need.
3. Render the chart once with a simple `--set` override to confirm that Helm can change values from the command line.
4. Create a file named `config.yaml`.
5. In `config.yaml`, set a blog name, admin user, and email.
6. In `config.yaml`, keep the service internal with `ClusterIP`.
7. In `config.yaml`, enable persistence for both WordPress and MariaDB.
8. Pass the WordPress admin password, MariaDB password, and MariaDB root password with `--set`, not inside `config.yaml`.
9. Add at least one extra non-sensitive change that is not shown in class, so you have to inspect the chart values yourself.
10. Render the chart locally with `helm template` so the result already reflects both the `config.yaml` values and the passwords passed with `--set`.
11. Deploy the chart into the cluster with the same split: file for normal settings, `--set` for passwords.

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
4. Make all resource names depend on the Helm release name so the rendered resources look like `release-name-resource-name`.
5. Update internal references so services, deployments, the ingress backend, and the database secret still point to the correct rendered names.
6. Do not hardcode the namespace in the templates; use the release namespace.
7. Make image repositories, tags, and pull policies configurable.
8. Make replica counts configurable.
9. Make service ports and container ports configurable.
10. Make the database secret values configurable.
11. Keep the frontend Nginx `ConfigMap` and the Postgres init SQL `ConfigMap` as provided in the starter chart.
12. Make the Postgres persistence size configurable.
13. Make the frontend ingress optional with `ingress.enabled`.
14. Make the ingress host configurable.
15. Make the ingress class name configurable.
16. Keep the chart intentionally simple. You do not need to add advanced helpers, autoscaling, probes, or chart dependencies.
17. Render the chart locally and review the generated manifests.
18. Explain which parts are easier with Helm than with plain manifests or Kustomize.

Recommended implementation order:

1. Render the starter chart once with `helm template` and identify what is hardcoded.
2. Replace one easy value such as a replica count with a value from `values.yaml`.
3. Replace one image string with a value from `values.yaml`.
4. Replace one port with a value from `values.yaml`.
5. Repeat the same pattern for the other workloads and services until the main image, replica, and port values are configurable.
6. Change resource names so they depend on the release name, then fix every internal reference that breaks because of the new names.
7. Remove hardcoded namespaces from templates and use the release namespace.
8. Move the database secret values into `values.yaml`.
9. Move the Postgres persistence size into `values.yaml`.
10. Make the ingress optional with `ingress.enabled`.
11. Add `ingress.host` and `ingress.className` values and wire them into the ingress template.
12. Render again and verify that the chart still produces a complete application with the new naming and ingress settings.
