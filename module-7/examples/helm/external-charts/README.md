# Module 7 Helm Existing Charts

These files support the first Helm workflows used in the module:
consume an existing chart and customize it with a values file.

- `wordpress-values.yaml`: reference values for the student WordPress exercise, without credentials.
- `grafana-values.yaml`: instructor example values for a simple observability UI deployment.

## Suggested Commands

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami
```

Adds the Bitnami repository for the WordPress exercise.

```bash
helm repo add grafana https://grafana.github.io/helm-charts
```

Adds the Grafana repository for the Grafana example.

```bash
helm template blog bitnami/wordpress --set service.type=ClusterIP
```

Renders the WordPress chart locally with a quick CLI override.

```bash
helm template blog bitnami/wordpress -f ./config.yaml
```

Renders the WordPress chart locally with the student's config file.

```bash
helm template grafana grafana/grafana -n observability --set adminPassword='grafana123'
```

Renders the Grafana chart locally with a single CLI override.

```bash
helm template grafana grafana/grafana -n observability -f module-7/examples/helm/external-charts/grafana-values.yaml --set adminPassword='grafana123'
```

Renders the Grafana chart locally with the example values plus a one-off CLI override.

```bash
helm install blog bitnami/wordpress -n blog --create-namespace -f ./config.yaml
```

Installs WordPress into a dedicated namespace for the exercise.

```bash
helm install grafana grafana/grafana -n observability --create-namespace -f module-7/examples/helm/external-charts/grafana-values.yaml --set adminPassword='grafana123'
```

Installs Grafana into an observability namespace for the instructor-led example, adding a single override from the command line.
