# Module 7: Continuous Integration and Delivery

## Module Goal

This module connects application packaging with automated delivery workflows.
The goal is for students to understand:

- what Helm solves;
- how a chart packages Kubernetes resources;
- what belongs in templates and what belongs in values;
- how optional features can be expressed through chart values.

## Module Assets

- `examples/helm/whoami-python/`: instructor reference chart for `whoami-python`, including a test and optional init container support.
- `examples/kompose/`: read-only example output from `kompose convert` using the course Compose file.
- `solutions/helm/demo-app/`: reference chart for the course demo application.

## Recommended Teaching Narrative

### Block 1: What Helm Adds

Key points:

- repeated Kubernetes manifests create duplication;
- Helm packages a set of related resources;
- values allow controlled variation without copying templates.

### Block 2: Templates and Values

Key points:

- templates define structure;
- values define environment-specific inputs;
- rendering happens before apply.

### Block 3: Positioning Other Tools

Key points:

- Kompose as an onboarding bridge from Compose;
- Kustomize for patch-based variation;
- CI pipelines for build and delivery automation;
- Argo CD for GitOps reconciliation.

This block is theoretical in this module.
The hands-on work stays focused on Helm.

## In-Class Demos

### Demo 1: Read the Chart Structure

Goal:
understand the moving parts of a small but complete chart.

Steps:

1. Open `examples/helm/whoami-python/Chart.yaml`.
2. Open `examples/helm/whoami-python/values.yaml`.
3. Open the templates directory.
4. Identify helpers, config, workload, service, and test resources.

### Demo 2: Render the Chart

Goal:
show how values become plain Kubernetes manifests.

Steps:

1. Run `helm template whoami-python examples/helm/whoami-python/`.
2. Identify the rendered `ConfigMap`, `Deployment`, `Service`, and test pod.
3. Change the message value and render again.

### Demo 3: Enable the Optional `initContainer`

Goal:
show how a chart can expose optional functionality without duplicating templates.

Steps:

1. Review the sample chart in `examples/helm/whoami-python/`.
2. Set `initContainer.enabled=true`.
3. Render the chart again.
4. Explain which parts of the pod spec only appear when the option is enabled.

### Demo 4: Read a Kompose Conversion

Goal:
show what an automatic Compose-to-Kubernetes translation looks like before manual refinement.

Steps:

1. Review `examples/kompose/README.md`.
2. Open `examples/kompose/docker-compose.go.kompose.yaml`.
3. Identify the generated `Deployment`, `Service`, and `PersistentVolumeClaim` objects.
4. Explain what still needs manual design work after the conversion.

## Exercise Direction

Use `whoami-python` as the compact reference chart.
The student exercise is to apply the same Helm patterns to `demo-app`.
Treat the Kompose example only as a discussion aid, not as the main practical path.

## Teaching Tips

- Keep the practical work focused on Helm.
- Mention CI, Kustomize, Kompose, and GitOps only as tool positioning unless you plan to teach them hands-on elsewhere.
- Keep the chart small enough that students can read every template in one sitting.
- Use `whoami-python` as the readable example and `demo-app` as the real charting exercise.
