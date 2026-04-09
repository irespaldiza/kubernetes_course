# Module 4 Exercises

These exercises reuse the same application components from the previous modules.
Keep `examples/` reserved for the published instructor manifests. The reference answers for the application manifests belong in `solutions/`.

## 1. Move the Frontend Nginx Configuration into a `ConfigMap`

Goal:
separate web server configuration from the frontend image.

## Suggested Commands

```bash
kubectl create configmap frontend-nginx --from-file=default.conf=module-0/demo-app/frontend/nginx.conf -n demo-app --dry-run=client -o yaml
```

Generates the `ConfigMap` manifest from the existing Nginx configuration file.

```bash
kubectl apply -f frontend-deployment.yaml -n demo-app
```

Applies the updated frontend deployment after the volume mount is added.

## Tasks

1. Read `module-0/demo-app/frontend/nginx.conf`.
2. Create a `ConfigMap` that contains that Nginx configuration.
3. Update the frontend deployment to mount the file at `/etc/nginx/conf.d/default.conf`.
4. Apply the new manifest.
5. Confirm that the frontend still serves the application and proxies both backend paths.

## 2. Move the Database Configuration into a Shared `Secret`

Goal:
separate sensitive database data from the manifests and keep it reusable across workloads.

## Suggested Commands

```bash
kubectl create secret generic database-secret -n demo-app --from-literal=POSTGRES_DB=appdb --from-literal=POSTGRES_USER=app --from-literal=POSTGRES_PASSWORD=app --from-literal=DATABASE_URL='postgres://app:app@postgres:5432/appdb?sslmode=disable' --dry-run=client -o yaml
```

Generates a reusable secret manifest with the database credentials and connection string.

```bash
kubectl apply -f database-secret.yaml -n demo-app
```

Creates or updates the shared `Secret` in the cluster.

## Tasks

1. Create one shared `Secret` for the database.
2. Store the database credentials there.
3. Include a `DATABASE_URL` entry that application pods can consume now.
4. Explain why the same secret could later also be used by the database pod itself.

## 3. Consume the Shared Database `Secret` from the Application Pods

Goal:
connect the shared secret to the workloads that need database access.

## Suggested Commands

```bash
kubectl apply -f catalog-service-deployment.yaml -f orders-service-deployment.yaml -n demo-app
```

Updates both workloads so they consume `DATABASE_URL` from the shared `Secret`.

```bash
kubectl rollout status deployment/catalog-service -n demo-app
```

Waits for the `catalog-service` rollout to complete.

```bash
kubectl rollout status deployment/orders-service -n demo-app
```

Waits for the `orders-service` rollout to complete.

## Tasks

1. Update `catalog-service` so `DATABASE_URL` comes from `secretKeyRef`.
2. Update `orders-service` so `DATABASE_URL` comes from the same shared secret.
3. Keep `CATALOG_URL` as a normal non-secret environment variable.
4. Apply the manifests and verify that both workloads still start.

## 4. Rotate the Shared Database `Secret`

Goal:
connect secret changes to workload operations.

## Suggested Commands

```bash
kubectl apply -f database-secret.yaml -n demo-app
```

Updates the secret after one of its values changes.

```bash
kubectl rollout restart deployment/catalog-service -n demo-app
```

Restarts one affected deployment so new pods read the updated secret values.

```bash
kubectl rollout restart deployment/orders-service -n demo-app
```

Restarts the second affected deployment for the same reason.

## Tasks

1. Update one value in the shared database secret.
2. Apply the change.
3. Restart the affected deployment or deployments.
4. Confirm that the pods use the new value.
