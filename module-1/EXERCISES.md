# Module 1 Exercises

## 1. Validate the Local Kubernetes Environment

Goal:
confirm that Docker Desktop Kubernetes is enabled and that `kubectl` can talk to the local cluster.

Tasks:

1. Enable Kubernetes in Docker Desktop.
2. Wait until the local cluster reports as ready.
3. Run `kubectl version`.
4. Run `kubectl config get-contexts` and identify the current context.
5. Run `kubectl config current-context`.
6. Run `kubectl cluster-info`.
7. Run `kubectl get nodes`.
8. Run `kubectl get namespaces`.

## 2. Inspect the Kubernetes System Components

Goal:
identify the platform components that already exist before any course workload is deployed.

Tasks:

1. Run `kubectl get pods -A`.
2. Identify the namespace where system components run.
3. Locate the DNS component.
4. Locate the control plane components exposed by Docker Desktop.
5. Choose one system pod and inspect it with `kubectl describe`.

## 3. Explore the Kubernetes API Surface

Goal:
understand that Kubernetes is an API platform with many resource types.

Tasks:

1. Run `kubectl api-resources`.
2. Identify which resources are namespaced.
3. Identify which resources belong to the `apps` API group.
4. Find the resource used to manage replicated stateless applications.
5. Run `kubectl explain deployment`.
