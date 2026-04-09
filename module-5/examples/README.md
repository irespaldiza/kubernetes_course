# Module 5 Examples

These examples focus on ephemeral storage and pod initialization.

- `whoami-emptydir-init-pod.yaml`: `emptyDir` prepared by an `initContainer` and consumed by the Module 4 `whoami` app.

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
