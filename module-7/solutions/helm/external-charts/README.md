# Module 7 External Chart Reference Values

These files are reference answers for the exercises where students consume existing charts before building their own.

- `wordpress-values.yaml`: reference values for `bitnami/wordpress`, without credentials.
- `grafana-values.yaml`: reference values for `grafana/grafana`.

For the WordPress exercise, keep normal configuration in `wordpress-values.yaml` and pass passwords at runtime with `--set`.

Use `wordpress-values.yaml` for:

- `wordpressBlogName`
- `wordpressUsername`
- `wordpressEmail`
- `service.type`
- WordPress persistence settings
- MariaDB persistence settings
- other non-sensitive chart values

Pass these credentials with `--set`:

- `wordpressPassword`
- `mariadb.auth.password`
- `mariadb.auth.rootPassword`

Suggested commands:

```bash
export WP_PASSWORD='student123'
export DB_PASSWORD='dbpass123'
export DB_ROOT_PASSWORD='rootpass123'
helm template blog bitnami/wordpress -f module-7/solutions/helm/external-charts/wordpress-values.yaml \
  --set wordpressPassword="$WP_PASSWORD" \
  --set mariadb.auth.password="$DB_PASSWORD" \
  --set mariadb.auth.rootPassword="$DB_ROOT_PASSWORD"
```

```bash
export WP_PASSWORD='student123'
export DB_PASSWORD='dbpass123'
export DB_ROOT_PASSWORD='rootpass123'
helm install blog bitnami/wordpress -n blog --create-namespace -f module-7/solutions/helm/external-charts/wordpress-values.yaml \
  --set wordpressPassword="$WP_PASSWORD" \
  --set mariadb.auth.password="$DB_PASSWORD" \
  --set mariadb.auth.rootPassword="$DB_ROOT_PASSWORD"
```

Use `helm template` first, then `helm install` if the cluster is ready.
