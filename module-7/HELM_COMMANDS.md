# Helm Commands

This file lists the Helm commands used most often in the course.

## Client and Repositories

```bash
helm version
```

Shows the installed Helm client version.

```bash
helm repo list
```

Lists configured chart repositories.

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

Adds a chart repository.

```bash
helm repo update
```

Refreshes repository metadata.

## Inspecting Charts

```bash
helm search repo wordpress
```

Searches repositories for charts.

```bash
helm show values bitnami/wordpress
```

Displays the default values of a chart.

```bash
helm show chart bitnami/wordpress
```

Displays chart metadata.

## Rendering and Validation

```bash
helm lint module-7/examples/helm/whoami-python
```

Checks chart structure and common template issues.

```bash
helm template whoami-python module-7/examples/helm/whoami-python
```

Renders a chart locally without installing it.

```bash
helm template blog bitnami/wordpress -f wordpress-values.yaml
```

Renders an external chart with a values file.

```bash
helm template blog bitnami/wordpress --set service.type=ClusterIP
```

Renders an external chart with a one-off CLI override.

```bash
helm template grafana grafana/grafana -n observability -f grafana-values.yaml --set adminPassword='grafana123'
```

Combines a values file with a one-off CLI override.

## Installing and Upgrading

```bash
helm install blog bitnami/wordpress -n blog --create-namespace -f wordpress-values.yaml
```

Installs a chart in a namespace.

```bash
helm upgrade blog bitnami/wordpress -n blog -f wordpress-values.yaml
```

Upgrades an installed release.

```bash
helm upgrade --install blog bitnami/wordpress -n blog --create-namespace -f wordpress-values.yaml
```

Installs or upgrades in one command.

## Release Management

```bash
helm list -A
```

Lists releases across namespaces.

```bash
helm status blog -n blog
```

Shows the status of one release.

```bash
helm get values blog -n blog
```

Shows the values used by an installed release.

```bash
helm get manifest blog -n blog
```

Shows the rendered manifests of an installed release.

```bash
helm history blog -n blog
```

Shows the revision history of a release.

```bash
helm rollback blog 1 -n blog
```

Rolls the release back to a previous revision.

```bash
helm uninstall blog -n blog
```

Removes a release.
