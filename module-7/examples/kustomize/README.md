# Module 7 Kustomize Example

This example keeps the application intentionally small so students can focus on how Kustomize works.

- `whoami-python/base/`: raw reusable manifests.
- `whoami-python/overlays/dev/`: development overlay with namespace, labels, one env patch, image override, and replica override.
- `whoami-python/overlays/prod/`: production overlay with a different namespace, labels, message, image tag, and replica count.

## Suggested Commands

```bash
kubectl kustomize module-7/examples/kustomize/whoami-python/base
```

Renders the base manifests with no environment-specific changes.

```bash
kubectl kustomize module-7/examples/kustomize/whoami-python/overlays/dev
```

Renders the overlay locally and shows the final manifest set.

```bash
kubectl kustomize module-7/examples/kustomize/whoami-python/overlays/prod
```

Renders the production overlay locally and shows how it differs from `dev`.

```bash
kubectl apply -k module-7/examples/kustomize/whoami-python/overlays/dev
```

Applies the overlay directly to the cluster.

```bash
kubectl apply -k module-7/examples/kustomize/whoami-python/overlays/prod
```

Applies the production overlay directly to the cluster.

```bash
kubectl get all -n demo-app
```

Confirms the rendered resources were created in the expected namespace.

```bash
kubectl get all -n demo-app-prod
```

Confirms the production overlay resources were created in the expected namespace.

## Patch Examples

This example is a good place to explain that Kustomize patches can be written in a fragile way or in a more robust way.

Fragile JSON Patch by index:

```yaml
patches:
  - target:
      kind: Deployment
      name: whoami-python
    patch: |-
      - op: replace
        path: /spec/template/spec/containers/0/env/1/value
        value: hello from kustomize dev
```

Why it is fragile:

- it assumes the container is still `containers[0]`
- it assumes `MESSAGE` is still `env[1]`
- if the order changes, the patch may stop working or update the wrong field

More robust strategic merge patch by `name`:

```yaml
patches:
  - target:
      kind: Deployment
      name: whoami-python
    patch: |-
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: whoami-python
      spec:
        template:
          spec:
            containers:
              - name: whoami-python
                env:
                  - name: MESSAGE
                    value: hello from kustomize dev
```

Why it is better:

- it identifies the container by `name`
- it identifies the env entry by `name`
- it does not depend on the order of the YAML lists
- it is usually easier to maintain
