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

### Demo 3: Explain Why a Database Often Uses StatefulSet

Goal:
connect storage to workload identity.

Steps:

1. Review `solutions/postgres-statefulset.yaml`.
2. Explain stable pod names and headless service usage.
3. Compare it with a `Deployment`.

## Teaching Tips

- Do not oversell `StatefulSet` as mandatory for every stateful app.
- Distinguish persistence from backup.
- If the training cluster uses dynamic provisioning, say that explicitly.
- Keep the only checked-in instructor example centered on `emptyDir`; leave the persistent database as the exercise solution.
