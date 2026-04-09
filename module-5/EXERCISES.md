# Module 5 Exercises

These exercises focus on persistent storage for the database workload.
Keep `examples/` for the published instructor example and `solutions/` for the reference answers.

## 1. Attach a PVC to Postgres

Goal:
run a database workload with persistent storage.

## Suggested Commands

```bash
kubectl apply -f postgres-init-configmap.yaml -f postgres-pvc.yaml -n demo-app
```

Creates the SQL initialization `ConfigMap`, the `PersistentVolumeClaim`, and the PostgreSQL workload.

```bash
kubectl get pvc,pods -n demo-app
```

Shows whether the claim is bound and whether the database pod is running.

```bash
kubectl exec -n demo-app deploy/postgres -- psql -U app -d appdb -c "SELECT * FROM products;"
```

Connects to the running database and reads the seeded data.

## Tasks

1. Create a `PersistentVolumeClaim` for PostgreSQL data.
2. Create a PostgreSQL workload that mounts that claim at `/var/lib/postgresql/data`.
3. Configure the database credentials from the shared `database-secret`.
4. Apply the manifests.
5. Connect to the database, create one table, and insert one row.
6. Restart the workload and confirm the row still exists.

## 2. Read a StatefulSet Manifest

Goal:
identify the pieces that make stable identity possible.

## Suggested Commands

```bash
kubectl apply --dry-run=client -f module-5/solutions/postgres-statefulset.yaml
```

Validates the `StatefulSet` manifest locally without creating resources.

```bash
kubectl explain statefulset.spec.volumeClaimTemplates
```

Shows the field used to create one claim per replica.

## Tasks

1. Open `solutions/postgres-statefulset.yaml`.
2. Identify the headless service.
3. Identify the `volumeClaimTemplates`.
4. Explain how pod names are assigned.

## 3. Compare `Deployment` and `StatefulSet` for Postgres

Goal:
decide which controller fits the database better at this stage of the course.

## Suggested Commands

```bash
kubectl get deployment,statefulset,svc,pvc -n demo-app
```

Shows the main controller and storage resources relevant to the comparison.

## Tasks

1. Review your PVC-backed PostgreSQL workload.
2. Review `solutions/postgres-statefulset.yaml`.
3. Identify the headless service.
4. Explain what `StatefulSet` adds beyond the PVC itself.
