Tiny Go web server that prints the container hostname.

You can change the listening port with the `PORT` environment variable.
It also prints `MY_ENV_VAR` and `SECRET_MESSAGE` when they are present.

It also exposes `/health` for a simple readiness check.

Local build:

```bash
docker build -t module2/whoami:local .
```

Run locally:

```bash
docker run --rm -p 8080:8080 -e PORT=8080 -e MY_ENV_VAR=local-demo -e SECRET_MESSAGE=local-secret module2/whoami:local
```
