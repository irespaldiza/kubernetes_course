# Module 7 Examples

These examples focus on Helm packaging for the Module 4 `whoami-python` application.

- `helm/whoami-python/`: Helm chart for `whoami-python`, including a Helm test and optional init container.
- `kompose/`: read-only example of converting `docker-compose.go.yml` into Kubernetes manifests with Kompose.

## Suggested Commands

```bash
helm lint module-7/examples/helm/whoami-python
```

Checks the chart structure and basic template validity.

```bash
helm template whoami-python module-7/examples/helm/whoami-python
```

Renders the chart locally without sending anything to the cluster.

```bash
helm install whoami-python module-7/examples/helm/whoami-python -n demo-app --create-namespace
```

Installs the chart into the `demo-app` namespace and creates the namespace if it does not exist yet.

```bash
helm test whoami-python -n demo-app
```

Runs the chart test pod declared in the Helm templates.
