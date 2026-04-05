# Module 2 Exercises

## 1. Run `catalog-service` as a Pod

Goal:
start one real application component as a Kubernetes pod.

Tasks:

1. Build `module0/catalog-service:local` if needed.
2. Create `catalog-pod.yaml`.
3. Apply the manifest.
4. Run `kubectl get pods`.
5. Port-forward the pod to local port `8000`.
6. Test `GET /health` and `GET /products`.

## 2. Run `orders-service` as a Pod

Goal:
deploy a second application component as a standalone pod.

Tasks:

1. Build `module0/orders-service-go:local` if needed.
2. Create `orders-pod.yaml`.
3. Apply the manifest.
4. Run `kubectl describe pod orders-service`.
5. Port-forward the pod to local port `8080`.
6. Test `GET /health` and `GET /orders`.

## 3. Replace the Pods with Deployments

Goal:
move from single-instance pods to controller-managed workloads.

Tasks:

1. Create `catalog-deployment.yaml`.
2. Create `orders-deployment.yaml`.
3. Apply both manifests.
4. List the pods created by both deployments.
5. Scale one deployment to `2` replicas.
6. Explain which part of the manifest connects the deployment to its pods.

## 4. Inspect Runtime State

Goal:
practice reading pod status, metadata, and runtime information directly from Kubernetes.

Tasks:

1. Run `kubectl get pods -n demo-app -o wide`.
2. Run `kubectl describe deployment catalog-service -n demo-app`.
3. Identify the pod IP, image, labels, and node name for one running pod.
4. Identify the declared container port for each application.
5. Explain which fields belong to desired state and which are reported by the cluster at runtime.
