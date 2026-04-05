# Module 1: Architecture and the Declarative Model

## Module Goal

This module introduces Kubernetes as a platform made of control-plane components and API objects.
The goal is for students to understand:

- what the Kubernetes control plane does;
- how resources are stored and exposed through the API;
- why YAML manifests are a desired state, not an imperative script;
- how reconciliation keeps the cluster aligned with that desired state.

## Why This Module Matters

Students often learn `kubectl apply` before they understand what the cluster is doing with that manifest.
This module creates the mental model needed for the rest of the course:

- the API server accepts resource definitions;
- controllers watch resources and act on differences;
- the scheduler assigns work to nodes;
- reconciliation loops keep pushing actual state toward desired state.

## Recommended Teaching Narrative

### Block 1: What Kubernetes Is

Explain Kubernetes as a distributed control system, not only as a container runner.

Key points:

- API server as the front door;
- etcd as cluster state storage;
- scheduler as placement logic;
- controller manager as the source of reconciliation loops;
- kubelet as the node agent.

### Block 2: API Objects as the Language of the Platform

Show that every important action becomes an object:

- namespaces;
- pods;
- deployments;
- services;
- config maps;
- secrets.

Key points:

- `apiVersion`, `kind`, `metadata`, `spec`, and `status`;
- labels and selectors;
- namespaced versus cluster-scoped resources.

### Block 3: Desired State and Reconciliation

Use a simple deployment example to show:

1. the user applies a manifest;
2. Kubernetes records the desired state;
3. a controller creates or replaces the missing workload;
4. drift is corrected automatically.

## In-Class Demos

### Demo 1: Read a Manifest Like an API Object

Goal:
make YAML less magical and more mechanical.

Steps:

1. Open `examples/configmap.yaml`.
2. Identify `apiVersion`, `kind`, `metadata`, and `data`.
3. Apply it and inspect it with `kubectl get configmap -o yaml`.

### Demo 2: Show Reconciliation in Action

Goal:
prove that the cluster keeps enforcing the desired state.

Steps:

1. Apply `examples/reconciliation-deployment.yaml`.
2. Wait for the image to be pulled and the pods to become `Running`.
3. Delete one pod manually.
4. Watch the deployment recreate it.
5. Scale replicas and observe the new state.

### Demo 3: Explain Scope with a Namespace

Goal:
show that Kubernetes organizes resources with explicit boundaries.

Steps:

1. Apply `examples/namespace.yaml`.
2. Create resources inside `demo-architecture`.
3. Compare `kubectl get pods` with and without `-n`.

## Teaching Tips

- Avoid starting with many resource kinds at once.
- Use `kubectl explain` to reinforce that manifests map to API schemas.
- Keep the first reconciliation demo visually simple.
- Repeat that `status` is observed state and `spec` is desired state.
