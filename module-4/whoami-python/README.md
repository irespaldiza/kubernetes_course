# Module 4 Whoami

Small Python web server used only in Module 4.

It exposes:

- `/health`: returns `ok`
- `/`: returns the pod hostname plus `MESSAGE` and `SECRET_MESSAGE`

Local build:

```bash
docker build -t module4/whoami-python:local .
```

Run locally:

```bash
docker run --rm -p 8080:8080 -e PORT=8080 -e MESSAGE=hello -e SECRET_MESSAGE=top-secret module4/whoami-python:local
```
