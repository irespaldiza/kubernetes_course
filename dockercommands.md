# Docker & Docker Compose Cheat Sheet

This document summarizes the most important Docker and Docker Compose commands, along with clear explanations.

---

# 🐳 Docker Basics

## Check Docker Installation

```bash
docker version
```

Shows the installed Docker version and client/server details.

```bash
docker info
```

Displays system-wide information about Docker.

---

# 📦 Images

## List Images

```bash
docker images
```

Lists all locally available images.

## Pull an Image

```bash
docker pull nginx
```

Downloads an image from Docker Hub.

## Build an Image

```bash
docker build -t my-app .
```

Builds an image from a Dockerfile in the current directory.

## Remove an Image

```bash
docker rmi my-app
```

Deletes an image from the local system.

---

# 📦 Containers

## Run a Container

```bash
docker run nginx
```

Runs a container from the nginx image.

## Run in Detached Mode

```bash
docker run -d nginx
```

Runs the container in the background.

## Run with Port Mapping

```bash
docker run -p 8080:80 nginx
```

Maps port 8080 on your host to port 80 in the container.

## List Running Containers

```bash
docker ps
```

## List All Containers

```bash
docker ps -a
```

## Stop a Container

```bash
docker stop <container_id>
```

## Start a Container

```bash
docker start <container_id>
```

## Restart a Container

```bash
docker restart <container_id>
```

## Remove a Container

```bash
docker rm <container_id>
```

---

# 🔍 Inspect & Logs

## View Logs

```bash
docker logs <container_id>
```

## Follow Logs

```bash
docker logs -f <container_id>
```

## Inspect Container

```bash
docker inspect <container_id>
```

---

# 🧪 Execute Commands

## Access a Running Container

```bash
docker exec -it <container_id> /bin/bash
```

Opens an interactive shell inside the container.

---

# 🧹 Cleanup

## Remove All Stopped Containers

```bash
docker container prune
```

## Remove Unused Images

```bash
docker image prune
```

## Remove Everything Unused

```bash
docker system prune
```

---

# 📁 Volumes

## List Volumes

```bash
docker volume ls
```

## Create a Volume

```bash
docker volume create my-volume
```

## Remove a Volume

```bash
docker volume rm my-volume
```

---

# 🌐 Networks

## List Networks

```bash
docker network ls
```

## Create a Network

```bash
docker network create my-network
```

---

# 🐙 Docker Compose

Docker Compose is used to define and run multi-container applications.

## Start Services

```bash
docker compose up
```

Builds, creates, and starts all services.

## Start in Detached Mode

```bash
docker compose up -d
```

## Stop Services

```bash
docker compose down
```

Stops and removes containers, networks, and volumes.

## Restart Services

```bash
docker compose restart
```

## Build Services

```bash
docker compose build
```

## View Logs

```bash
docker compose logs
```

## Follow Logs

```bash
docker compose logs -f
```

## List Services

```bash
docker compose ps
```

## Execute Command in Service

```bash
docker compose exec <service_name> sh

```
