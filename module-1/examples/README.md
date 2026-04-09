# Module 1 Examples

These examples support the architecture and declarative model discussion.

- `namespace.yaml`: minimal namespace boundary.
- `configmap.yaml`: small API object to read and inspect.
- `reconciliation-deployment.yaml`: simple deployment based on `nginx:1.27-alpine` to demonstrate controller behavior with a public image.

## Suggested Commands

```bash
kubectl apply -f module-1/examples/namespace.yaml
```

Creates the `demo-app` namespace used by later workloads.

```bash
kubectl apply -f module-1/examples/configmap.yaml
```

Creates a small `ConfigMap` so students can inspect a basic API object.

```bash
kubectl apply -f module-1/examples/reconciliation-deployment.yaml
```

Deploys an `nginx` workload managed by a `Deployment`.

```bash
kubectl get all -n demo-app
```

Shows the namespace resources created by the previous commands.

```bash
kubectl delete pod -n demo-app -l app=reconciliation-demo
```

Deletes the running pod so students can observe that the `Deployment` recreates it.
