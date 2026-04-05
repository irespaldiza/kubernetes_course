# Module 1 Examples

These examples support the architecture and declarative model discussion.

- `namespace.yaml`: minimal namespace boundary.
- `configmap.yaml`: small API object to read and inspect.
- `reconciliation-deployment.yaml`: simple deployment based on `nginx:1.27-alpine` to demonstrate controller behavior with a public image.

Suggested flow:

1. Apply `namespace.yaml`.
2. Apply `configmap.yaml`.
3. Apply `reconciliation-deployment.yaml`.
4. Delete one pod and observe reconciliation.
