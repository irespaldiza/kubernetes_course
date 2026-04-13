# Module 5 Examples

These examples cover ephemeral storage, persistent claims, and config-backed files.

- `whoami-emptydir-init-pod.yaml`: `emptyDir` prepared by an `initContainer` and consumed by the Module 4 `whoami` app.
- `pvc-demo-pod.yaml`: `PersistentVolumeClaim` plus a pod that appends lines to a file on mounted storage.
- `configmap-file-volume-pod.yaml`: `ConfigMap` mounted as a single file inside a container with `subPath`.
- `identity-demo-statefulset.yaml`: headless `Service` plus a simple `StatefulSet` that writes each pod name into its own persistent volume.

## Suggested Commands

```bash
kubectl apply -f module-5/examples/whoami-emptydir-init-pod.yaml
```

Creates a pod that writes data in an `initContainer` before the main container starts.

```bash
kubectl get pod whoami-emptydir -n demo-app
```

Confirms that the pod reached the running state after the init phase completed.

```bash
kubectl exec -n demo-app pod/whoami-emptydir -- cat /workspace/startup.txt
```

Reads the file written into the shared `emptyDir` volume.

```bash
kubectl delete pod whoami-emptydir -n demo-app
```

Removes the pod so students can discuss why the `emptyDir` data disappears with it.

```bash
kubectl apply -f module-5/examples/pvc-demo-pod.yaml
```

Creates a PVC and a pod that writes `/data/history.log` on persistent storage.

```bash
kubectl exec -n demo-app pod/pvc-demo -- cat /data/history.log
```

Shows the file stored on the mounted claim.

```bash
kubectl delete pod pvc-demo -n demo-app
kubectl apply -f module-5/examples/pvc-demo-pod.yaml
kubectl exec -n demo-app pod/pvc-demo -- cat /data/history.log
```

Demonstrates that the file remains after recreating the pod because the PVC still exists.

```bash
kubectl apply -f module-5/examples/configmap-file-volume-pod.yaml
```

Creates a `ConfigMap` and mounts `app.properties` as a file inside the container.

```bash
kubectl exec -n demo-app pod/configmap-file-demo -- cat /config/app.properties
```

Reads the file that was projected from the `ConfigMap` volume.

```bash
kubectl apply -f module-5/examples/identity-demo-statefulset.yaml
```

Creates a headless `Service` and a two-replica `StatefulSet`.

```bash
kubectl get statefulset,pods,pvc -n demo-app -l app=identity-demo
```

Shows the stable pod names and the PVC created for each replica.

```bash
kubectl exec -n demo-app pod/identity-demo-0 -- cat /data/pod-name.txt
kubectl exec -n demo-app pod/identity-demo-1 -- cat /data/pod-name.txt
```

Confirms that each replica writes its own hostname into its own persistent volume.
