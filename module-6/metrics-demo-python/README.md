# Module 6 Metrics Demo

Small Python web server used only in Module 6.

It exposes:

- `/health`: returns `ok`
- `/`: returns the pod hostname plus `MESSAGE`
- `/metrics`: returns a minimal Prometheus-style metric

Local build:

```bash
docker build -t module6/metrics-demo:local .
```

Run locally:

```bash
docker run --rm -p 8080:8080 -e PORT=8080 -e MESSAGE=hello module6/metrics-demo:local
```
