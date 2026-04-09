# Module 7: Continuous Integration and Delivery

## Module Goal

This module connects application packaging with automated delivery workflows.
The goal is for students to understand:

- what plain Kubernetes manifests look like when gathered into one application bundle;
- what Helm solves;
- what Kustomize solves;
- how a chart packages Kubernetes resources;
- how a `kustomization.yaml` groups and transforms a manifest set;
- what belongs in templates and what belongs in values;
- how optional features can be expressed through chart values.

## Module Assets

- `examples/helm/whoami-python/`: instructor reference chart for `whoami-python`, including a test and optional init container support.
- `examples/kompose/`: read-only example output from `kompose convert` using the course Compose file.
- `solutions/manifests/demo-app/all.yaml`: reference bundle with the full application as plain manifests.
- `solutions/kustomize/demo-app/`: reference Kustomize packaging for the same application.
- `solutions/helm/demo-app/`: reference chart for the course demo application.

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
- this is useful when variation is mostly patching or composition.

### Block 3: What Helm Adds

Key points:

- repeated Kubernetes manifests create duplication and parameter repetition;
- Helm packages a set of related resources;
- values allow controlled variation without copying templates.

### Block 4: Templates and Values

Key points:

- templates define structure;
- values define environment-specific inputs;
- rendering happens before apply.

### Block 5: Positioning Other Tools

Key points:

- Kompose as an onboarding bridge from Compose;
- Kustomize for patch-based variation and manifest composition;
- CI pipelines for build and delivery automation;
- Argo CD for GitOps reconciliation.

This block can now be partly hands-on through the checked-in Kustomize solution.

## In-Class Demos

### Demo 1: Read the Chart Structure

Goal:
understand the moving parts of a small but complete chart.

Steps:

1. Open `examples/helm/whoami-python/Chart.yaml`.
2. Open `examples/helm/whoami-python/values.yaml`.
3. Open the templates directory.
4. Identify helpers, config, workload, service, and test resources.

### Demo 2: Read the Plain Manifest Bundle

Goal:
show the complete application with no packaging layer.

Steps:

1. Open `solutions/manifests/demo-app/all.yaml`.
2. Identify namespace, config, secret, services, deployments, and PVC.
3. Explain which parts are repeated across components.

### Demo 3: Render Kustomize

Goal:
show a lightweight packaging layer that still stays close to raw manifests.

Steps:

1. Run `kubectl kustomize solutions/kustomize/demo-app/`.
2. Compare the rendered output with `solutions/manifests/demo-app/all.yaml`.
3. Explain what Kustomize centralizes and what still stays explicit.

### Demo 4: Render the Chart

Goal:
show how values become plain Kubernetes manifests.

Steps:

1. Run `helm template demo-app solutions/helm/demo-app/`.
2. Identify the rendered `ConfigMap`, `Deployment`, `Service`, `Secret`, and PVC.
3. Change one value and render again.

### Demo 5: Compare the Three Packaging Styles

Goal:
position plain manifests, Kustomize, and Helm for different team needs.

Steps:

1. Compare `solutions/manifests/demo-app/all.yaml`.
2. Compare `solutions/kustomize/demo-app/kustomization.yaml`.
3. Compare `solutions/helm/demo-app/Chart.yaml`.
4. Explain which approach is easiest to read, vary, and reuse.

### Demo 6: Read a Kompose Conversion

Goal:
show what an automatic Compose-to-Kubernetes translation looks like before manual refinement.

Steps:

1. Review `examples/kompose/README.md`.
2. Open `examples/kompose/docker-compose.go.kompose.yaml`.
3. Identify the generated `Deployment`, `Service`, and `PersistentVolumeClaim` objects.
4. Explain what still needs manual design work after the conversion.

## Exercise Direction

Use `whoami-python` as the compact reference chart.
The student exercise is to package `demo-app` in three ways: plain manifests, Kustomize, and Helm.
Treat the Kompose example only as a discussion aid, not as the main practical path.

## Teaching Tips

- Start from plain manifests before abstraction so students see what the tools are wrapping.
- Use Kustomize as the minimal packaging step and Helm as the more parameter-driven step.
- Mention CI, Kompose, and GitOps as adjacent tools after the packaging comparison.
- Keep the chart small enough that students can read every template in one sitting.
- Use `whoami-python` as the readable Helm example and `demo-app` as the real packaging exercise.
