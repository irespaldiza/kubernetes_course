# Module 7 Examples

These are instructor examples used to explain packaging approaches before or alongside the Helm exercises.

- `kustomize/whoami-python/`: small Kustomize example with a base plus `dev` and `prod` overlays focused on simple transformations.
- `helm/whoami-python/`: small Helm chart used to explain chart structure.
- `helm/demo-app-starter/`: shared starting chart for the `demo-app` exercise, close to the plain manifests in `currentState/`.
- `helm/external-charts/grafana-values.yaml`: example values file for consuming the Grafana chart.
- `kompose/`: read-only example of converting `docker-compose.go.yml` into Kubernetes manifests with Kompose.

WordPress is not listed here because it is treated as a student exercise in `../EXERCISES.md`.

The full `demo-app` reference still exists in:

- `../solutions/manifests/demo-app/`
- `../solutions/kustomize/demo-app/`
- `../solutions/helm/demo-app/`

## Suggested Commands

```bash
kubectl kustomize module-7/examples/kustomize/whoami-python/base
```

Renders the plain base manifests with no environment-specific changes.

```bash
kubectl kustomize module-7/examples/kustomize/whoami-python/overlays/dev
```

Renders the Kustomize overlay locally.

```bash
kubectl kustomize module-7/examples/kustomize/whoami-python/overlays/prod
```

Renders the production overlay locally for comparison with `dev`.

```bash
helm lint module-7/examples/helm/whoami-python
```

Checks the reference chart structure and basic template validity.

```bash
helm template whoami-python module-7/examples/helm/whoami-python
```

Renders the reference chart locally without sending anything to the cluster.

```bash
helm template grafana grafana/grafana -n observability -f module-7/examples/helm/external-charts/grafana-values.yaml
```

Renders the Grafana chart for the observability discussion.

```bash
kubectl apply -f module-7/solutions/manifests/demo-app/all.yaml
```

Applies the plain-manifest bundle for the same application without Helm or Kustomize.
