# Module 2 Examples

These examples focus on workload execution.

- `whoami-pod.yaml`: instructor pod demo that shows pod identity through HTTP.
- `whoami-deployment.yaml`: instructor deployment demo used to contrast a single pod with a controller-managed workload.

The application manifests for `catalog-service`, `orders-service`, `frontend`, and `postgres` are exercise solutions and should not live in this published `examples/` directory.

## Suggested Commands

```bash
kubectl apply -f module-2/examples/whoami-pod.yaml
```

Starts one standalone pod so students can see the smallest runnable workload unit.

```bash
kubectl get pods -n demo-app -o wide
```

Lists the pod name, status, IP, and node placement.

```bash
kubectl port-forward -n demo-app pod/whoami 8080:8080
```

Exposes the pod locally so students can call it directly without a `Service`.

```bash
kubectl apply -f module-2/examples/whoami-deployment.yaml
```

Creates a controller-managed version of the same application.

```bash
kubectl scale deployment/whoami -n demo-app --replicas=3
```

Changes the desired replica count to show how a `Deployment` manages multiple pods.
