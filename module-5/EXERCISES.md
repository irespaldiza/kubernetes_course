# Module 5 Exercises

These exercises focus on persistent storage for the database workload.
Keep `examples/` for the published instructor example and `solutions/` for the reference answers.

## 1. Attach a PVC to Postgres

Goal:
run a database workload with persistent storage.

Tasks:

1. Create a `PersistentVolumeClaim` for PostgreSQL data.
2. Create a PostgreSQL workload that mounts that claim at `/var/lib/postgresql/data`.
3. Configure the database credentials from the shared `database-secret`.
4. Apply the manifests.
5. Connect to the database, create one table, and insert one row.
6. Restart the workload and confirm the row still exists.

## 2. Read a StatefulSet Manifest

Goal:
identify the pieces that make stable identity possible.

Tasks:

1. Open `solutions/postgres-statefulset.yaml`.
2. Identify the headless service.
3. Identify the `volumeClaimTemplates`.
4. Explain how pod names are assigned.

## 3. Compare `Deployment` and `StatefulSet` for Postgres

Goal:
decide which controller fits the database better at this stage of the course.

Tasks:

1. Review your PVC-backed PostgreSQL workload.
2. Review `solutions/postgres-statefulset.yaml`.
3. Identify the headless service.
4. Explain what `StatefulSet` adds beyond the PVC itself.
