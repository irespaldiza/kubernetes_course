# Module 6 Examples

These examples focus on basic operational visibility.

- `logs-demo-pod.yaml`: simple pod that emits logs continuously.
- `whoami-probes-deployment.yaml`: deployment with liveness and readiness probes.
- `metrics-demo-deployment.yaml`: small Python app that exposes a real `/metrics` endpoint.
- `metrics-demo-service.yaml`: service annotated for Prometheus-style scraping.
- `fluent-bit-configmap.yaml`: example Fluent Bit configuration for tailing container logs.
- `fluent-bit-daemonset.yaml`: example DaemonSet that mounts the Fluent Bit configuration.
