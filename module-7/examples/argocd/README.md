# Module 7 Argo CD Examples

These examples show how Argo CD reconciles the same packaging approaches already used in this module:

- Kustomize overlays
- Helm charts
- multi-environment generation with `ApplicationSet`

All examples point to real paths inside this repository.

## Files

- `argocd-server-ingress.yaml`: `Ingress` for exposing the Argo CD UI through the cluster ingress controller
- `whoami-kustomize-dev.yaml`: Argo CD `Application` for the `dev` Kustomize overlay
- `whoami-kustomize-prod.yaml`: Argo CD `Application` for the `prod` Kustomize overlay
- `whoami-helm.yaml`: Argo CD `Application` for the small `whoami-python` chart
- `demo-app-helm.yaml`: Argo CD `Application` for the `demo-app` solution chart
- `whoami-applicationset.yaml`: Argo CD `ApplicationSet` that generates one app per environment for the Kustomize example

## Prerequisites

Before applying any Argo CD example:

- install Argo CD in the cluster
- make sure Argo CD can reach this Git repository
- replace the placeholder Git URL in each file

Replace:

```yaml
repoURL: https://github.com/YOUR_ORG/kubernetes_course.git
```

with the real repository URL, for example your fork of this repository.

## Install Argo CD

Create the namespace:

```bash
kubectl create namespace argocd
```

Install Argo CD:

```bash
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
```

Wait for the Argo CD pods:

```bash
kubectl get pods -n argocd
```

Expose the Argo CD UI through Ingress:

```bash
kubectl apply -f module-7/examples/argocd/argocd-server-ingress.yaml
```

For a simple lab setup behind an ingress controller, configure `argocd-server` in insecure mode so the ingress handles the external HTTP endpoint and Argo CD does not redirect back to HTTPS:

```bash
kubectl patch configmap argocd-cmd-params-cm -n argocd --type merge -p '{"data":{"server.insecure":"true"}}'
kubectl rollout restart deployment argocd-server -n argocd
```

Check the ingress:

```bash
kubectl get ingress -n argocd
```

The example ingress uses:

```text
argocd.demo.local
```

Make sure that hostname resolves to your ingress controller entrypoint.

Then open the Argo CD UI through:

```text
http://argocd.demo.local
```

If you open `https://argocd.demo.local` without configuring TLS on the ingress, the browser will show an untrusted certificate warning and may also end up in a redirect loop.

Get the initial admin password:

```bash
kubectl get secret argocd-initial-admin-secret -n argocd -o jsonpath="{.data.password}" | base64 --decode && echo
```

## Apply the Examples

Apply one Kustomize example:

```bash
kubectl apply -f module-7/examples/argocd/whoami-kustomize-dev.yaml
```

Apply the second Kustomize example:

```bash
kubectl apply -f module-7/examples/argocd/whoami-kustomize-prod.yaml
```

Apply the small Helm example:

```bash
kubectl apply -f module-7/examples/argocd/whoami-helm.yaml
```

Apply the `demo-app` Helm example:

```bash
kubectl apply -f module-7/examples/argocd/demo-app-helm.yaml
```

Apply the generated multi-environment example:

```bash
kubectl apply -f module-7/examples/argocd/whoami-applicationset.yaml
```

## Verify the Result

List the Argo CD applications:

```bash
kubectl get applications -n argocd
```

Describe one application:

```bash
kubectl describe application whoami-helm -n argocd
```

Check the reconciled workloads:

```bash
kubectl get all -n demo-app
kubectl get all -n demo-app-prod
kubectl get all -n whoami-helm
```

Check the `demo-app` release managed by Argo CD:

```bash
kubectl get all -n demo-app
kubectl get ingress -n demo-app
```

## Suggested Teaching Flow

1. Start with `whoami-kustomize-dev.yaml` to show the simplest GitOps flow.
2. Compare `dev` and `prod` using the two Kustomize applications.
3. Move to `whoami-helm.yaml` to show Argo CD rendering Helm without running `helm install`.
4. Apply `demo-app-helm.yaml` to connect Argo CD with the custom Helm chart built in this module.
5. Finish with `whoami-applicationset.yaml` to show how repeated applications are generated instead of written by hand.

## Key Idea

Argo CD does not replace manifests, Kustomize, or Helm.

It watches Git, renders the selected packaging format, and reconciles the cluster to match the desired state stored in the repository.
