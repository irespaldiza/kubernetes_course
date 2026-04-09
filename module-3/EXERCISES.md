# Module 3 Exercises

These exercises assume that the `catalog-service`, `orders-service`, and `frontend` deployments from Module 2 are already running in the `demo-app` namespace.
Keep `examples/` reserved for the published instructor manifests. The reference answers for these services and the application ingress belong in `solutions/`.

## 1. Create a Service for `catalog-service`

Goal:
give a stable internal name to one application component.

## Suggested Commands

```bash
kubectl apply -f catalog-service.yaml -n demo-app
```

Creates the `Service` defined by the student.

```bash
kubectl get svc,endpoints -n demo-app
```

Shows the service and the backend pods selected behind it.

```bash
kubectl port-forward -n demo-app service/catalog-service 8000:8000
```

Exposes the `Service` locally so students can call it through the stable service endpoint.

## Tasks

1. Create `catalog-service.yaml`.
2. Apply the manifest.
3. Run `kubectl get svc -n demo-app`.
4. Port-forward the service to local port `8000`.
5. Test `GET /health` and `GET /products`.
6. Identify the selector that connects the service to its pods.

## 2. Create a Service for `orders-service`

Goal:
connect one backend service to another through internal DNS.

## Suggested Commands

```bash
kubectl apply -f orders-service.yaml -n demo-app
```

Creates the `orders-service` `Service`.

```bash
kubectl port-forward -n demo-app service/orders-service 8080:8080
```

Exposes the service locally for HTTP testing.

```bash
curl -X POST http://127.0.0.1:8080/orders -H 'Content-Type: application/json' -d '{"product_id":1,"customer":"alice"}'
```

Sends a sample create-order request that depends on `catalog-service` being resolvable inside the cluster.

## Tasks

1. Create `orders-service.yaml`.
2. Apply the manifest.
3. Port-forward the service to local port `8080`.
4. Test `GET /health` and `GET /orders`.
5. Send a `POST /orders` request with `product_id=1` and a sample customer.
6. Explain why the request depends on the service name `catalog-service`.

## 3. Expose the Frontend Inside the Cluster

Goal:
complete the internal networking of the demo application.

## Suggested Commands

```bash
kubectl apply -f frontend-service.yaml -n demo-app
```

Creates the service in front of the frontend deployment.

```bash
kubectl port-forward -n demo-app service/frontend 8080:80
```

Exposes the frontend service locally in the browser.

## Tasks

1. Create `frontend-service.yaml`.
2. Apply the manifest.
3. Port-forward the frontend service to local port `8080`.
4. Open the application in the browser.
5. Confirm that the frontend can load products and orders through service names.
6. Identify which service names are referenced by the frontend configuration.

## 4. Observe Service Load Balancing

Goal:
see that a service targets a group of pods, not only one pod.

## Suggested Commands

```bash
kubectl scale deployment/catalog-service -n demo-app --replicas=2
```

Creates multiple backend pods behind the same service name.

```bash
kubectl get pods -n demo-app -l app=catalog-service
```

Shows the pod set currently selected by the service.

## Tasks

1. Scale `catalog-service` or `orders-service` to `2` replicas.
2. Run `kubectl get pods -n demo-app`.
3. Keep using the same service endpoint.
4. Explain why the client still connects through the same service name even when the backend pod set changes.
