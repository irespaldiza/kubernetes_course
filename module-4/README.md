# Module 4: Configuration and Secrets

## Module Goal

This module teaches students how to inject configuration without baking it into the image.
The goal is for them to understand:

- what belongs in a `ConfigMap`;
- what belongs in a `Secret`;
- how configuration reaches a pod as environment variables or mounted files;
- how configuration changes affect application rollout and operations.

## Module Assets

- `whoami-python/`: small Python application used only in this module.
- `examples/whoami-configmap.yaml`: instructor `ConfigMap` example for non-sensitive values.
- `examples/whoami-secret.yaml`: instructor `Secret` example for sensitive values.
- `examples/whoami-deployment.yaml`: instructor deployment that consumes both resources.
- `solutions/`: reference manifests for the exercise answers, kept separate from `examples/`.

## Recommended Teaching Narrative

### Block 1: Keep Images Generic

Key points:

- build once, configure per environment;
- image content should not contain deployment-specific values;
- configuration moves slower than code in some cases and faster in others.

### Block 2: ConfigMaps and Secrets

Key points:

- `ConfigMap` for non-sensitive values;
- `Secret` for sensitive values;
- base64 encoding is transport formatting, not security by itself.

### Block 3: Injecting Configuration into Workloads

Key points:

- `env`;
- `envFrom`;
- mounted files;
- rollout implications when values change.

## In-Class Demos

### Demo 1: Externalize `whoami` Configuration

Goal:
show how non-sensitive and sensitive values reach a pod.

Steps:

1. Build the image from `whoami-python/` with the tag `module4/whoami-python:local`.
2. Apply `examples/whoami-configmap.yaml`.
3. Apply `examples/whoami-secret.yaml`.
4. Apply `examples/whoami-deployment.yaml`.
5. Open the application and confirm that both environment-backed values are visible.

### Demo 2: Show a Secret from the Pod Viewpoint

Goal:
make clear that the pod sees the final value, not only the manifest reference.

Steps:

1. Use `kubectl exec` into one `whoami-configured` pod.
2. Print the environment variables.
3. Explain why repository storage still requires care.

## Teaching Tips

- Do not teach plaintext secrets in Git as an acceptable final pattern.
- Explain that secret management in production usually involves an external system.
- Keep one example with `valueFrom` because it is explicit and easy to read.
- Keep the application manifests for `frontend`, `catalog-service`, and `orders-service` in `solutions/`, not in `examples/`.
- Frame the database secret as shared infrastructure data, not as something owned by one application only.
