# Module 2 Exercises

## 1. Run `catalog-service` as a Pod

Goal:
start one real application component as a Kubernetes pod.

## Suggested Commands

```bash
docker build -t module0/catalog-service:local module-0/demo-app/catalog-service
```

Builds the local image used by the exercise.

```bash
kubectl apply -f module-1/examples/namespace.yaml
```

Creates the `demo-app` namespace if it does not exist yet.

```bash
kubectl apply -f catalog-pod.yaml -n demo-app
```

Creates the standalone pod from the manifest the student writes.

```bash
kubectl port-forward -n demo-app pod/catalog-service 8000:8000
```

Exposes the pod locally so students can call the HTTP endpoints directly.

## Tasks

1. Build `module0/catalog-service:local` if needed.
2. Create `catalog-pod.yaml`.
3. Apply the manifest.
4. Run `kubectl get pods`.
5. Port-forward the pod to local port `8000`.
6. Test `GET /health` and `GET /products`.

## 2. Run `orders-service` as a Pod

Goal:
deploy a second application component as a standalone pod.

## Suggested Commands

```bash
docker build -t module0/orders-service-go:local module-0/demo-app/orders-service
```

Builds the local Go service image.

```bash
kubectl apply -f orders-pod.yaml -n demo-app
```

Creates the pod from the manifest the student writes.

```bash
kubectl describe pod orders-service -n demo-app
```

Shows the effective container image, ports, events, and environment wiring.

```bash
kubectl port-forward -n demo-app pod/orders-service 8080:8080
```

Exposes the pod locally for HTTP testing.

## Tasks

1. Build `module0/orders-service-go:local` if needed.
2. Create `orders-pod.yaml`.
3. Apply the manifest.
4. Run `kubectl describe pod orders-service`.
5. Port-forward the pod to local port `8080`.
6. Test `GET /health` and `GET /orders`.

## 3. Replace the Pods with Deployments

Goal:
move from single-instance pods to controller-managed workloads.

## Suggested Commands

```bash
kubectl apply -f catalog-deployment.yaml -f orders-deployment.yaml -n demo-app
```

Creates or updates both deployments from the manifests the student writes.

```bash
kubectl get deployments,pods -n demo-app
```

Shows the controllers and the pods they created.

```bash
kubectl scale deployment/catalog-service -n demo-app --replicas=2
```

Changes the desired state so students can observe replica management.

## Tasks

1. Create `catalog-deployment.yaml`.
2. Create `orders-deployment.yaml`.
3. Apply both manifests.
4. List the pods created by both deployments.
5. Scale one deployment to `2` replicas.
6. Explain which part of the manifest connects the deployment to its pods.

## 4. Inspect Runtime State

Goal:
practice reading pod status, metadata, and runtime information directly from Kubernetes.

## Suggested Commands

```bash
kubectl get pods -n demo-app -o wide
```

Shows runtime details such as pod IPs and node placement.

```bash
kubectl describe deployment catalog-service -n demo-app
```

Shows the desired state, selector, template, and rollout status for the deployment.

## Tasks

1. Run `kubectl get pods -n demo-app -o wide`.
2. Run `kubectl describe deployment catalog-service -n demo-app`.
3. Identify the pod IP, image, labels, and node name for one running pod.
4. Identify the declared container port for each application.
5. Explain which fields belong to desired state and which are reported by the cluster at runtime.
