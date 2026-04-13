# Module 5: Persistence

## Module Goal

This module explains how Kubernetes handles stateful workloads and storage lifecycles.
The goal is for students to understand:

- the difference between ephemeral and persistent storage;
- when `emptyDir` is useful;
- how an `initContainer` can prepare data before the main container starts;
- how a `PersistentVolumeClaim` is consumed by a workload;
- why `StatefulSet` exists and when it is a better fit than `Deployment`.

## Module Assets

- `examples/whoami-emptydir-init-pod.yaml`: instructor example for `emptyDir` plus `initContainer`.
- `examples/pvc-demo-pod.yaml`: simple PVC example with a pod that writes a file on persistent storage.
- `examples/configmap-file-volume-pod.yaml`: example of mounting a `ConfigMap` key as a file inside a container.
- `examples/identity-demo-statefulset.yaml`: instructor example of a simple `StatefulSet` with a headless `Service` and one PVC per replica.
- `solutions/postgres-pvc.yaml`: reference solution for a PostgreSQL deployment backed by a PVC.
- `solutions/postgres-statefulset.yaml`: optional reference showing the same database with `StatefulSet`.

## Recommended Teaching Narrative

### Block 1: Ephemeral Storage

Key points:

- container filesystems are disposable;
- `emptyDir` lives for the lifetime of the pod;
- an `initContainer` can seed files into an `emptyDir` before the app starts;
- ephemeral storage is useful for scratch space, caches, and temporary processing.

### Block 2: Stable Storage Through Claims

Key points:

- workloads request storage through a `PersistentVolumeClaim`;
- the cluster binds that request to actual storage;
- the application should depend on the claim, not on infrastructure details.

Suggested example:

- `examples/pvc-demo-pod.yaml`

### Block 2.5: ConfigMap as a Mounted File

Key points:

- a `ConfigMap` can be projected as files in a volume;
- each key becomes a file unless `subPath` is used to mount a single file;
- this is useful for config files consumed from well-known paths.

Suggested example:

- `examples/configmap-file-volume-pod.yaml`

### Block 3: StatefulSet

Key points:

- stable identity;
- ordered rollout and restart behavior;
- per-replica storage for databases or brokers.

## In-Class Demos

### Demo 1: Use `emptyDir` for Shared Temporary Data

Goal:
show storage that is prepared at startup and disappears with the pod.

Steps:

1. Build the image from `module-4/whoami-python/` with the tag `module4/whoami-python:local` if needed.
2. Apply `examples/whoami-emptydir-init-pod.yaml`.
3. Inspect `/workspace/startup.txt` from the running container.
4. Explain that the file was created by the `initContainer`.
5. Delete the pod and show that the data disappears.

### Demo 2: Explain Persistence with a PostgreSQL Workload

Goal:
connect PVC-backed storage to a realistic database example.

Steps:

1. Review `solutions/postgres-pvc.yaml`.
2. Identify the `PersistentVolumeClaim`.
3. Identify where Postgres mounts the claim.
4. Explain why the data outlives the container.

### Demo 2b: Mount a ConfigMap as a File

Goal:
show how configuration can be exposed as a real file path inside a container.

Steps:

1. Apply `examples/configmap-file-volume-pod.yaml`.
2. Inspect `/config/app.properties` inside the running pod.
3. Explain the role of `subPath` in mounting a single key as one file.
4. Compare this pattern with environment variables.

### Demo 3: Explain Why a Database Often Uses StatefulSet

Goal:
connect storage to workload identity.

Steps:

1. Review `examples/identity-demo-statefulset.yaml`.
2. Explain stable pod names and headless service usage.
3. Compare it with a `Deployment`.

## Teaching Tips

- Do not oversell `StatefulSet` as mandatory for every stateful app.
- Distinguish persistence from backup.
- If the training cluster uses dynamic provisioning, say that explicitly.
- Keep the examples small and focused; leave PostgreSQL as the exercise and solution, and use the generic `StatefulSet` example only to explain identity and per-replica storage.
