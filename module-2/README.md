# Module 2: Running Applications

## Module Goal

This module turns the course demo application into Kubernetes workloads.
The goal is for students to understand:

- what a `Pod` really is;
- how a container image becomes a running workload;
- how to inspect one pod directly with `kubectl port-forward`;
- why application components should later move from `Pod` to `Deployment`.

## Module Assets

- `whoami/`: tiny Go web server that returns the pod hostname.
- `examples/whoami-pod.yaml`: instructor pod demo based on the `whoami` image.
- `examples/whoami-deployment.yaml`: instructor deployment demo based on the same image.
- `solutions/`: reference manifests for the exercise answers, kept separate from `examples/`.

## Recommended Teaching Narrative

### Block 1: One Pod Is One Running Instance

Key points:

- a pod is the smallest unit Kubernetes schedules;
- a pod wraps one or more containers plus networking and storage context;
- a pod gets its own IP and name, but it is still disposable.

### Block 2: Direct Access with `port-forward`

Key points:

- `port-forward` is a debugging and learning tool;
- it lets students inspect one pod directly before introducing services;
- the response can make pod identity visible.

### Block 3: Turn Application Components into Deployments

Key points:

- a standalone pod is useful for learning, but not for normal application management;
- deployments create and replace pods automatically;
- the full application wiring is still incomplete until services are introduced in the next module.

## In-Class Demos

### Demo 1: Run One Pod and Show Its Identity

Goal:
show that a pod is a concrete runtime instance and that traffic can be sent directly to it.

Steps:

1. Build the image from `whoami/` with a stable local tag such as `module2/whoami:local`.
2. Apply `examples/whoami-pod.yaml`.
3. Inspect the pod with `kubectl get pod -o wide` and `kubectl describe`.
4. Port-forward the pod and open `/`.
5. Show that the response includes the pod hostname.

### Demo 2: Run One Course Component as a Pod

Goal:
connect the course application images to Kubernetes workloads.

Steps:

1. Create `catalog-pod.yaml` during class or review `solutions/catalog-pod.yaml` in the answers branch.
2. Apply the manifest.
3. Port-forward the pod and test `/health` and `/products`.
4. Explain why this works even though the full application is not wired yet.

### Demo 3: Replace Pods with Deployments

Goal:
show why controllers are the normal path for applications.

Steps:

1. Apply `examples/whoami-deployment.yaml`.
2. List the created pods and compare them with the standalone pod case.
3. Scale replicas from `2` to `3`.
4. Explain what the selector matches and why the deployment recreates pods automatically.

### Demo 4: Replace an Application Pod with a Deployment

Goal:
connect the controller model to one course application component.

Steps:

1. Create `catalog-deployment.yaml` during class or review `solutions/catalog-deployment.yaml` in the answers branch.
2. Apply the manifest.
3. Scale replicas from `1` to `3`.
4. List the created pods.
5. Explain why a stable entry point will be needed in the next module.

## Teaching Tips

- Keep the `whoami` demo because it makes pod identity visible immediately.
- Use `catalog-service` first because it still behaves sensibly without the database.
- Keep the application manifests out of `examples/`; they belong in `solutions/` or in the separate answers branch.
- Do not try to fully wire the application yet. That is the point of `Service` in Module 3.
