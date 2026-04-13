# Module 5 Exercises

## 1. Create the PostgreSQL StatefulSet

Goal:
create the PostgreSQL `StatefulSet` manifest.

The `Service` already exists.
Use the existing `postgres` headless service and the existing `postgres-init` `ConfigMap`.

Create `postgres-statefulset.yaml` with:

1. A `StatefulSet` named `postgres` in namespace `demo-app`.
2. `serviceName: postgres`.
3. One replica.
4. `app: postgres` in the selector and pod template labels.
5. Image `postgres:16-alpine`.
6. Container port `5432`.
7. `POSTGRES_DB`, `POSTGRES_USER`, and `POSTGRES_PASSWORD` loaded from `database-secret`.
8. `PGDATA=/var/lib/postgresql/data/pgdata`.
9. A volume mount named `postgres-data` at `/var/lib/postgresql/data`.
10. A volume mount named `postgres-init` at `/docker-entrypoint-initdb.d/init.sql` using `subPath: init.sql` and `readOnly: true`.
11. A `ConfigMap` volume named `postgres-init` using the existing `postgres-init` resource.
12. `volumeClaimTemplates` with a claim named `postgres-data`, `ReadWriteOnce`, and `1Gi`.

## Suggested Commands

```bash
kubectl apply -f module-5/solutions/postgres-init-configmap.yaml -f postgres-statefulset.yaml -n demo-app
```

Creates the init SQL `ConfigMap` if needed and applies the PostgreSQL `StatefulSet`.

```bash
kubectl get statefulset,pods,pvc -n demo-app -l app=postgres
```

Shows the `StatefulSet`, the pod `postgres-0`, and the generated PVC.

```bash
kubectl exec -n demo-app pod/postgres-0 -- psql -U app -d appdb -c "SELECT * FROM products;"
```

Checks that the database started correctly and that the seed data is available.
