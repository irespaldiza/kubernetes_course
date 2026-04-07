# Comandos principales de kubectl

## Información del clúster
- `kubectl cluster-info`
- `kubectl version`

## Gestión de recursos
- `kubectl get pods`
- `kubectl get services`
- `kubectl get deployments`
- `kubectl describe pod <nombre>`
- `kubectl delete pod <nombre>`
- `kubectl apply -f <archivo.yaml>`
- `kubectl create -f <archivo.yaml>`

## Logs y debugging
- `kubectl logs <pod>`
- `kubectl logs -f <pod>`
- `kubectl exec -it <pod> -- /bin/sh`

## Configuración y contexto
- `kubectl config get-contexts`
- `kubectl config use-context <contexto>`
- `kubectl config current-context`

## Escalado y despliegues
- `kubectl scale deployment <nombre> --replicas=3`
- `kubectl rollout status deployment <nombre>`
- `kubectl rollout undo deployment <nombre>`

## Namespaces
- `kubectl get namespaces`
- `kubectl create namespace <nombre>`
- `kubectl delete namespace <nombre>`

## Otros útiles
- `kubectl port-forward <pod> 8080:80`
- `kubectl top pod`
- `kubectl top node`
