# Module 6 Exercises

These exercises reuse the course application components from previous modules.
Keep `examples/` for the published instructor manifests and `solutions/` for the reference answers.

## 1. Inspect Application Logs

Goal:
build the habit of reading logs before editing manifests.

Tasks:

1. Apply `examples/logs-demo-pod.yaml`.
2. Read the logs with `kubectl logs`.
3. Filter the pod by label.
4. Explain what the process is doing.

## 2. Add Probes to an Existing Deployment

Goal:
connect health endpoints to Kubernetes behavior.

Tasks:

1. Start from `solutions/catalog-service-observability.yaml`.
2. Add a startup probe.
3. Apply the manifest.
4. Explain the difference between readiness and liveness.

## 3. Expose a Metrics Endpoint

Goal:
understand the minimum shape of a scrape-ready workload.

Tasks:

1. Review `examples/metrics-demo-deployment.yaml`.
2. Review `examples/metrics-demo-service.yaml`.
3. Identify the metrics port and scrape annotations.
4. Describe what extra platform component is required to consume the metrics.

## 4. Read a Fluent Bit Configuration

Goal:
understand the basic shape of a cluster log shipping setup.

Tasks:

1. Review `examples/fluent-bit-configmap.yaml`.
2. Identify the input plugin, Kubernetes filter, and output plugin.
3. Review `examples/fluent-bit-daemonset.yaml`.
4. Explain why a logging agent commonly runs as a `DaemonSet`.
