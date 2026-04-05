# Module 4 Exercises

These exercises reuse the same application components from the previous modules.
Keep `examples/` reserved for the published instructor manifests. The reference answers for the application manifests belong in `solutions/`.

## 1. Move the Frontend Nginx Configuration into a `ConfigMap`

Goal:
separate web server configuration from the frontend image.

Tasks:

1. Read `module-0/demo-app/frontend/nginx.conf`.
2. Create a `ConfigMap` that contains that Nginx configuration.
3. Update the frontend deployment to mount the file at `/etc/nginx/conf.d/default.conf`.
4. Apply the new manifest.
5. Confirm that the frontend still serves the application and proxies both backend paths.

## 2. Move the Database Configuration into a Shared `Secret`

Goal:
separate sensitive database data from the manifests and keep it reusable across workloads.

Tasks:

1. Create one shared `Secret` for the database.
2. Store the database credentials there.
3. Include a `DATABASE_URL` entry that application pods can consume now.
4. Explain why the same secret could later also be used by the database pod itself.

## 3. Consume the Shared Database `Secret` from the Application Pods

Goal:
connect the shared secret to the workloads that need database access.

Tasks:

1. Update `catalog-service` so `DATABASE_URL` comes from `secretKeyRef`.
2. Update `orders-service` so `DATABASE_URL` comes from the same shared secret.
3. Keep `CATALOG_URL` as a normal non-secret environment variable.
4. Apply the manifests and verify that both workloads still start.

## 4. Rotate the Shared Database `Secret`

Goal:
connect secret changes to workload operations.

Tasks:

1. Update one value in the shared database secret.
2. Apply the change.
3. Restart the affected deployment or deployments.
4. Confirm that the pods use the new value.
