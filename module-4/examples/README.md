# Module 4 Examples

These examples use `whoami` to demonstrate configuration and secrets without mixing in the exercise answers.

- `whoami-configmap.yaml`: non-sensitive environment values.
- `whoami-secret.yaml`: secret value injected as an environment variable.
- `whoami-deployment.yaml`: deployment for the Module 4 Python `whoami` app that consumes both resources explicitly with `valueFrom`.
