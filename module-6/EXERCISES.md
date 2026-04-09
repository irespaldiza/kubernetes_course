# Module 6 Exercises

These exercises reuse the course application components from previous modules.
Keep `examples/` for the published instructor manifests and `solutions/` for the reference answers.

## 1. Inspect Application Logs

Goal:
build the habit of reading logs before editing manifests.

## Suggested Commands

```bash
kubectl apply -f module-6/examples/logs-demo-pod.yaml
```

Creates a pod that continuously emits logs.

```bash
kubectl logs -n demo-app pod/logs-demo -f
```

Streams the log output from the running pod.

```bash
kubectl get pods -n demo-app -l app=logs-demo
```

Filters pods by label to reinforce label-based operations.

## Tasks

1. Apply `examples/logs-demo-pod.yaml`.
2. Read the logs with `kubectl logs`.
3. Filter the pod by label.
4. Explain what the process is doing.

## 2. Add Probes to an Existing Deployment

Goal:
connect health endpoints to Kubernetes behavior.

## Suggested Commands

```bash
kubectl apply -f module-6/solutions/catalog-service-observability.yaml -n demo-app
```

Applies the reference manifest that includes probes for `catalog-service`.

```bash
kubectl describe deployment catalog-service -n demo-app
```

Shows the configured readiness, liveness, and startup probes.

## Tasks

1. Start from `solutions/catalog-service-observability.yaml`.
2. Add a startup probe.
3. Apply the manifest.
4. Explain the difference between readiness and liveness.

## 3. Expose a Metrics Endpoint

Goal:
understand the minimum shape of a scrape-ready workload.

## Suggested Commands

```bash
kubectl apply -f module-6/examples/metrics-demo-deployment.yaml -f module-6/examples/metrics-demo-service.yaml
```

Deploys the demo application and the annotated service used for scraping.

```bash
kubectl port-forward -n demo-app service/metrics-demo 8080:8080
```

Exposes the metrics endpoint locally for inspection.

```bash
curl http://127.0.0.1:8080/metrics
```

Reads the raw metrics output exposed by the application.

## Tasks

1. Review `examples/metrics-demo-deployment.yaml`.
2. Review `examples/metrics-demo-service.yaml`.
3. Identify the metrics port and scrape annotations.
4. Describe what extra platform component is required to consume the metrics.

## 4. Read a Fluent Bit Configuration

Goal:
understand the basic shape of a cluster log shipping setup.

## Suggested Commands

```bash
kubectl apply --dry-run=client -f module-6/examples/fluent-bit-configmap.yaml -f module-6/examples/fluent-bit-daemonset.yaml
```

Validates both manifests locally without deploying them.

## Tasks

1. Review `examples/fluent-bit-configmap.yaml`.
2. Identify the input plugin, Kubernetes filter, and output plugin.
3. Review `examples/fluent-bit-daemonset.yaml`.
4. Explain why a logging agent commonly runs as a `DaemonSet`.
