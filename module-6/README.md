# Module 6: Observability

## Module Goal

This module introduces the minimum operational signals needed to understand what workloads are doing.
The goal is for students to understand:

- how to inspect container logs;
- how probes relate to application health;
- how metrics collection usually starts;
- how observability supports troubleshooting instead of replacing it.

## Module Assets

- `examples/logs-demo-pod.yaml`: instructor log-reading demo.
- `examples/whoami-probes-deployment.yaml`: instructor probe demo based on the Module 4 app.
- `metrics-demo-python/`: small Python application used only for the metrics example.
- `examples/metrics-demo-deployment.yaml`: instructor metrics endpoint demo.
- `examples/metrics-demo-service.yaml`: service annotated for Prometheus-style scraping.
- `examples/fluent-bit-configmap.yaml`: example Fluent Bit configuration.
- `examples/fluent-bit-daemonset.yaml`: example Fluent Bit DaemonSet.
- `solutions/`: reference manifests for observability changes on the course application.

## Recommended Teaching Narrative

### Block 1: Logs as the First Source of Truth

Key points:

- `kubectl logs`;
- current container versus previous container;
- label selectors for multiple pods.

### Block 2: Health Signals

Key points:

- liveness;
- readiness;
- startup probes when boot time matters;
- probe failures affect routing and restart behavior.

### Block 3: Metrics and Scraping

Key points:

- application metrics endpoint;
- service annotations in Prometheus-style setups;
- metrics help identify trends that logs do not show.

## In-Class Demos

### Demo 1: Read Logs from a Noisy Pod

Goal:
practice the quickest feedback loop during failure analysis.

Steps:

1. Apply `examples/logs-demo-pod.yaml`.
2. Read logs from the running pod.
3. Restart it and inspect previous logs if needed.

### Demo 2: Show the Effect of Readiness and Liveness

Goal:
connect probes to traffic and restart behavior.

Steps:

1. Build the image from `module-4/whoami-python/` with the tag `module4/whoami-python:local` if needed.
2. Apply `examples/whoami-probes-deployment.yaml`.
3. Inspect probe configuration.
4. Explain what happens if `/health` stops responding.

### Demo 3: Explain Metrics Discovery

Goal:
show a realistic first step toward metrics scraping.

Steps:

1. Build the image from `metrics-demo-python/` with the tag `module6/metrics-demo:local`.
2. Apply `examples/metrics-demo-deployment.yaml`.
3. Review `examples/metrics-demo-service.yaml`.
4. Identify the scrape annotations and service port.
5. Explain that the actual collector is external to the manifest.

### Demo 4: Read a Fluent Bit Configuration

Goal:
connect container logs to a realistic log shipping agent.

Steps:

1. Review `examples/fluent-bit-configmap.yaml`.
2. Review `examples/fluent-bit-daemonset.yaml`.
3. Identify the input, filter, and output sections.
4. Explain why a log agent typically runs as a `DaemonSet`.

## Teaching Tips

- Keep the first troubleshooting loop centered on `describe`, `logs`, and `events`.
- Clarify that probes are not a full monitoring strategy.
- Avoid promising metrics collection without the corresponding stack installed.
- Keep the checked-in instructor examples separate from the observability changes students make to the course application.
- Use a workload that really exposes `/metrics`; do not pretend the course application already has one if it does not.
