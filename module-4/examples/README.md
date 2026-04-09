# Module 4 Examples

These examples use `whoami` to demonstrate configuration and secrets without mixing in the exercise answers.

- `whoami-configmap.yaml`: non-sensitive environment values.
- `whoami-secret.yaml`: secret value injected as an environment variable.
- `whoami-deployment.yaml`: deployment for the Module 4 Python `whoami` app that consumes both resources explicitly with `valueFrom`.

## Suggested Commands

```bash
docker build -t module4/whoami-python:local module-4/whoami-python
```

Builds the local image used by the configuration example.

```bash
kubectl apply -f module-4/examples/whoami-configmap.yaml
```

Creates the non-sensitive configuration values.

```bash
kubectl apply -f module-4/examples/whoami-secret.yaml
```

Creates the sensitive value stored as a Kubernetes `Secret`.

```bash
kubectl apply -f module-4/examples/whoami-deployment.yaml
```

Deploys the application and injects both resources into the container environment.

```bash
kubectl exec -n demo-app deploy/whoami-configured -- env | grep WHOAMI
```

Shows the final environment variables visible inside the running container.
