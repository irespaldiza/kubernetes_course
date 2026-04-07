# kubectl Cheat Sheet

This document summarizes the most important `kubectl` commands, along with clear explanations.

---

# ☸️ Cluster Information

## Show Cluster Information

```bash
kubectl cluster-info
```

Displays the control plane endpoint and the main cluster services.

## Check kubectl Version

```bash
kubectl version
```

Shows the client version and, when reachable, the server version.

## View API Resources

```bash
kubectl api-resources
```

Lists all available resource types exposed by the Kubernetes API.

---

# 📦 Common Resources

## List Pods

```bash
kubectl get pods
```

Lists pods in the current namespace.

## List Services

```bash
kubectl get services
```

Lists services in the current namespace.

## List Deployments

```bash
kubectl get deployments
```

Lists deployments in the current namespace.

## List All Resources

```bash
kubectl get all
```

Shows the most common workload and networking resources in the current namespace.

## Describe a Resource

```bash
kubectl describe pod <pod_name>
```

Displays detailed information, events, and status for a resource.

## Edit a Resource

```bash
kubectl edit deployment <deployment_name>
```

Opens the live resource manifest in your default editor for quick changes.

## Delete a Resource

```bash
kubectl delete pod <pod_name>
```

Deletes the specified resource from the cluster.

---

# 📝 Apply Manifests

## Apply a Manifest

```bash
kubectl apply -f <file.yaml>
```

Creates or updates resources from a manifest file.

## Create from a Manifest

```bash
kubectl create -f <file.yaml>
```

Creates resources from a manifest file without performing updates.

## Delete from a Manifest

```bash
kubectl delete -f <file.yaml>
```

Deletes the resources defined in a manifest file.

---

# 🔍 Logs And Debugging

## View Logs

```bash
kubectl logs <pod_name>
```

Shows logs from the main container in a pod.

## Follow Logs

```bash
kubectl logs -f <pod_name>
```

Streams pod logs in real time.

## View Logs for a Specific Container

```bash
kubectl logs <pod_name> -c <container_name>
```

Shows logs for a specific container inside a multi-container pod.

## Execute a Command in a Pod

```bash
kubectl exec -it <pod_name> -- /bin/sh
```

Opens an interactive shell inside a running container.

---

# ⚙️ Context And Configuration

## List Contexts

```bash
kubectl config get-contexts
```

Lists all configured Kubernetes contexts.

## Show Current Context

```bash
kubectl config current-context
```

Displays the context currently in use.

## Switch Context

```bash
kubectl config use-context <context_name>
```

Changes the active context used by `kubectl`.

## Show Current Namespace

```bash
kubectl config view --minify --output 'jsonpath={..namespace}'
```

Prints the default namespace for the active context.

## Set the Default Namespace

```bash
kubectl config set-context --current --namespace=<namespace_name>
```

Changes the default namespace for the current context.

---

# 📂 Namespaces

## List Namespaces

```bash
kubectl get namespaces
```

Lists all namespaces in the cluster.

## Create a Namespace

```bash
kubectl create namespace <namespace_name>
```

Creates a new namespace.

## Delete a Namespace

```bash
kubectl delete namespace <namespace_name>
```

Deletes the specified namespace and the resources inside it.

## Run a Command in a Specific Namespace

```bash
kubectl get pods -n <namespace_name>
```

Runs the command against the namespace you specify instead of the default one.

---

# 🚀 Scaling And Rollouts

## Scale a Deployment

```bash
kubectl scale deployment <deployment_name> --replicas=3
```

Changes the number of desired pod replicas for a deployment.

## Check Rollout Status

```bash
kubectl rollout status deployment <deployment_name>
```

Shows whether a deployment rollout has completed successfully.

## Restart a Deployment

```bash
kubectl rollout restart deployment <deployment_name>
```

Triggers a rolling restart of the deployment pods.

## Undo a Rollout

```bash
kubectl rollout undo deployment <deployment_name>
```

Rolls a deployment back to the previous revision.

---

# 🌐 Access And Metrics

## Port-Forward a Pod

```bash
kubectl port-forward pod/<pod_name> 8080:80
```

Forwards local port 8080 to port 80 in the selected pod.

## Show Pod Metrics

```bash
kubectl top pod
```

Displays CPU and memory usage for pods when Metrics Server is installed.

## Show Node Metrics

```bash
kubectl top node
```

Displays CPU and memory usage for nodes when Metrics Server is installed.
