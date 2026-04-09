# Module 6 Examples

These examples focus on basic operational visibility.

- `logs-demo-pod.yaml`: simple pod that emits logs continuously.
- `whoami-probes-deployment.yaml`: deployment with liveness and readiness probes.
- `metrics-demo-deployment.yaml`: small Python app that exposes a real `/metrics` endpoint.
- `metrics-demo-service.yaml`: service annotated for Prometheus-style scraping.
- `fluent-bit-configmap.yaml`: example Fluent Bit configuration for tailing container logs.
- `fluent-bit-daemonset.yaml`: example DaemonSet that mounts the Fluent Bit configuration.

## Suggested Commands

```bash
kubectl apply -f module-6/examples/logs-demo-pod.yaml
```

Starts a pod that emits continuous log lines.

```bash
kubectl logs -n demo-app pod/logs-demo -f
```

Streams the application logs in real time.

```bash
kubectl apply -f module-6/examples/whoami-probes-deployment.yaml
```

Deploys a workload that includes readiness and liveness probes.

```bash
kubectl apply -f module-6/examples/metrics-demo-deployment.yaml -f module-6/examples/metrics-demo-service.yaml
```

Creates a small metrics-enabled application and the `Service` used to expose and annotate it.

```bash
kubectl apply -f module-6/examples/fluent-bit-configmap.yaml -f module-6/examples/fluent-bit-daemonset.yaml
```

Deploys a node-level log collector example based on `DaemonSet`.
