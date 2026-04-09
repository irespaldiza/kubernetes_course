# Module 0 Exercises

These exercises use the demo project in [demo-app/README.md](/Users/irespaldiza/Documents/cursos/kubernetes/git/kubernetes_course/module-0/demo-app/README.md).

## 1. Install and Verify Docker

Goal:
make sure the local environment is ready before building any course image.

## Suggested Commands

```bash
docker version
```

Shows the Docker client and server versions and confirms that the daemon is reachable.

```bash
docker info
```

Prints runtime details such as the active engine, storage driver, and configured resources.

```bash
docker compose version
```

Confirms that the Compose plugin is installed and available.

## Tasks

1. Install Docker Desktop or another compatible local Docker runtime if it is not already installed.
2. Confirm that the Docker daemon is running.
3. Run `docker version`.
4. Run `docker info`.
5. Confirm that `docker compose` is available.
