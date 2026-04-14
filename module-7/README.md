# Module 7: Continuous Integration and Delivery

## Module Goal

This module connects application packaging with automated delivery workflows.
The goal is for students to understand:

- what plain Kubernetes manifests look like when gathered into one application bundle;
- what Kustomize solves when a team wants to stay close to raw YAML;
- what Helm solves when a team wants to consume or package reusable applications;
- how to install Helm and use an existing public chart before building a custom chart;
- how a chart packages Kubernetes resources;
- what belongs in templates and what belongs in values;
- what kind of delivery problem is best served by plain manifests, Kustomize, or Helm.

## Module Assets

Examples:

- `examples/kustomize/whoami-python/`: small Kustomize example with a base plus `dev` and `prod` overlays focused on simple transformations.
- `examples/helm/whoami-python/`: instructor reference chart for `whoami-python`, including a test and optional init container support.
- `examples/helm/external-charts/grafana-values.yaml`: example values file for consuming the Grafana chart.
- `examples/kompose/`: read-only example output from `kompose convert` using the course Compose file.

Exercises:

- `EXERCISES.md`: student exercises for Helm.

Reference solutions:

- `solutions/manifests/demo-app/all.yaml`: reference bundle with the full application as plain manifests.
- `solutions/kustomize/demo-app/`: reference Kustomize packaging for the same application.
- `solutions/helm/external-charts/wordpress-values.yaml`: reference values for the WordPress exercise, with credentials passed in the command.
- `solutions/helm/demo-app/`: reference chart for the course demo application.

Support material:

- `HELM_COMMANDS.md`: common Helm commands used in the module.
- `HELM_GO_TEMPLATE.md`: short guide to Go template usage in Helm charts.

## Recommended Teaching Narrative

### Block 1: Plain Manifests First

Key points:

- a full application can always be expressed as plain manifests;
- one file or one directory can gather the complete desired state;
- this is the baseline that packaging tools build on top of.

### Block 2: What Kustomize Adds

Key points:

- Kustomize keeps manifests close to raw YAML;
- a `kustomization.yaml` groups resources and applies controlled transformations;
- bases and overlays help teams avoid copying nearly identical manifests;
- this is useful when variation is mostly patching, image replacement, labels, or generated config.

### Block 3: Helm as a Consumer Tool

Key points:

- teams often use Helm first by installing existing charts;
- `helm repo add`, `helm show values`, and a simple `--set` override are the quickest way to understand how a chart changes;
- after that, a small custom values file becomes the maintainable way to keep configuration;
- students should learn this path before writing templates;
- this is a practical way to install tools like WordPress, Grafana, or other platform components.

### Block 4: Helm as a Packaging Tool

Key points:

- repeated Kubernetes manifests create duplication and parameter repetition;
- Helm packages a set of related resources;
- templates define structure;
- values define environment-specific inputs;
- rendering happens before apply.

### Block 5: Positioning Other Tools

Key points:

- Kompose as an onboarding bridge from Compose;
- Kustomize for patch-based variation and manifest composition;
- Helm for both consuming shared charts and packaging internal apps;
- CI pipelines for build and delivery automation;
- Argo CD for GitOps reconciliation.

## In-Class Examples

### Example 1: Read the Plain Manifest Bundle

Goal:
show the complete application with no packaging layer.

Steps:

1. Open `solutions/manifests/demo-app/all.yaml`.
2. Identify namespace, config, secret, services, deployments, and PVC.
3. Explain which parts are repeated across components.

### Example 2: Render a Small Kustomize Example

Goal:
show a lightweight packaging layer that still stays close to raw manifests.

Steps:

1. Review `examples/kustomize/whoami-python/base/`.
2. Review `examples/kustomize/whoami-python/overlays/dev/`.
3. Review `examples/kustomize/whoami-python/overlays/prod/`.
4. Run `kubectl kustomize module-7/examples/kustomize/whoami-python/base`.
5. Run `kubectl kustomize module-7/examples/kustomize/whoami-python/overlays/dev`.
6. Run `kubectl kustomize module-7/examples/kustomize/whoami-python/overlays/prod`.
7. Identify what stays in the base and what changes between `dev` and `prod`.

### Example 3: Render the Full Demo App with Kustomize

Goal:
connect the small Kustomize example with the real course application.

Steps:

1. Run `kubectl kustomize module-7/solutions/kustomize/demo-app/`.
2. Compare the rendered output with `solutions/manifests/demo-app/all.yaml`.
3. Explain what Kustomize centralizes and what still stays explicit.

### Example 4: Consume the Grafana Chart for Observability

Goal:
show that Helm is also a fast path for operational tooling, not only business applications.

Steps:

1. Review `examples/helm/external-charts/grafana-values.yaml`.
2. Run `helm repo add grafana https://grafana.github.io/helm-charts`.
3. Run `helm show values grafana/grafana`.
4. Run `helm template grafana grafana/grafana -n observability --set adminPassword='grafana123'`.
5. Run `helm template grafana grafana/grafana -n observability -f module-7/examples/helm/external-charts/grafana-values.yaml --set adminPassword='grafana123'`.
6. Explain that `--set` is the fastest way to demonstrate overrides, while the values file is the maintainable way to keep configuration.
7. Explain that Grafana can be introduced through an existing chart long before the team authors its own.

### Example 5: Read the Custom Chart Structure

Goal:
understand the moving parts of a small but complete chart.

Steps:

1. Open `examples/helm/whoami-python/Chart.yaml`.
2. Open `examples/helm/whoami-python/values.yaml`.
3. Open the templates directory.
4. Identify helpers, config, workload, service, and test resources.

### Example 6: Read a Kompose Conversion

Goal:
show what an automatic Compose-to-Kubernetes translation looks like before manual refinement.

Steps:

1. Review `examples/kompose/README.md`.
2. Open `examples/kompose/docker-compose.go.kompose.yaml`.
3. Identify the generated `Deployment`, `Service`, and `PersistentVolumeClaim` objects.
4. Explain what still needs manual design work after the conversion.

## Exercises

Students only do three Helm exercises in this module:

1. install Helm and verify the client;
2. deploy an existing WordPress chart with a student-created `config.yaml`;
3. create a chart for `demo-app`.

Kustomize and Kompose are examples in this module, not student exercises.
Grafana is also an example, not an exercise.

## Teaching Tips

- Start from plain manifests before abstraction so students see what the tools are wrapping.
- Keep Kustomize focused on composition and low-ceremony overrides before introducing generators or more advanced patches.
- Introduce Helm twice: first as a way to install existing software, later as a way to package internal software.
- Use Grafana as the instructor-led example and WordPress as the hands-on exercise so students do not repeat the same chart passively.
- Mention that values keys may change slightly between chart releases, so students should inspect `helm show values` before writing overrides.
- Keep the custom chart small enough that students can read every template in one sitting.
