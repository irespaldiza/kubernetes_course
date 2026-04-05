# Module 3: Networking and Internal Exposure

## Module Goal

This module explains how the course application components find each other inside the cluster.
The goal is for students to understand:

- why pods are not a stable network endpoint;
- how `Service` provides stable discovery;
- how internal DNS resolution works through service names;
- how one service can load-balance across multiple pod replicas;
- how `Ingress` exposes HTTP routes through a single external entry point.

## Module Assets

- `examples/whoami-deployment.yaml`: instructor deployment with multiple `whoami` replicas.
- `examples/whoami-service.yaml`: service used to demonstrate load balancing.
- `examples/whoami-ingress.yaml`: ingress rule for HTTP access to the `whoami` demo.
- `solutions/`: reference service and ingress manifests for the exercise answers, kept separate from `examples/`.

## Module Prerequisite

This module assumes that the application deployments from Module 2 are already running in the `demo-app` namespace.

## Recommended Teaching Narrative

### Block 1: Pods Change, Services Stay

Key points:

- every pod gets its own IP;
- pod IPs are not the contract other applications should depend on;
- `Service` creates a stable name and stable virtual IP.

### Block 2: Internal DNS

Key points:

- service names are resolved inside the cluster;
- the application can call `http://catalog-service:8000` without knowing pod IPs;
- labels and selectors connect the service to the right backend pods.

### Block 3: Service-Level Access and Load Balancing

Key points:

- `kubectl port-forward` can be done against a service as well as a pod;
- one service can distribute traffic across multiple pod replicas;
- this is the moment where the different application components start to work together.

### Block 4: HTTP Entry with Ingress

Key points:

- `Ingress` defines HTTP routing rules, not pod-to-pod discovery;
- the ingress controller is the component that implements those rules;
- the backend of an ingress route is usually a `Service`, not a pod;
- hostnames and paths become part of the application entry contract.

## In-Class Demos

### Demo 1: Load Balance `whoami` Through a Service

Goal:
show that the service is the entry point while pods remain replaceable backends.

Steps:

1. Apply `examples/whoami-deployment.yaml`.
2. Apply `examples/whoami-service.yaml`.
3. Port-forward the service.
4. Refresh the request multiple times.
5. Show that the response changes between pod hostnames.

### Demo 2: Add Services to the Course Application

Goal:
make the demo application components discover each other by name.

Steps:

1. Create `catalog-service.yaml` during class or review `solutions/catalog-service.yaml` in the answers branch.
2. Create `orders-service.yaml` during class or review `solutions/orders-service.yaml` in the answers branch.
3. Explain why `orders-service` can now call `catalog-service` by service name.
4. Confirm that the `frontend` deployment from Module 2 is already running.
5. Create `frontend-service.yaml` during class or review `solutions/frontend-service.yaml` in the answers branch.
6. Port-forward the frontend service and show the full application working.

### Demo 3: Publish `whoami` with Ingress

Goal:
show the difference between an internal service and an HTTP entry point.

Steps:

1. Confirm that an ingress controller is running in the cluster.
2. Apply `examples/whoami-ingress.yaml`.
3. Explain the host rule `whoami.demo.local`.
4. Add a local hosts entry if needed for the classroom setup.
5. Open the application through the ingress endpoint.

## Teaching Tips

- Make the DNS name explicit every time you reference a service.
- Repeat that a `Service` is not a container and not a proxy pod.
- Keep the application service and application ingress manifests out of `examples/`; they belong in `solutions/` or in the separate answers branch.
- Make the controller dependency explicit: an ingress manifest alone does not publish traffic.
- If the cluster does not include an ingress controller, keep the manifest as a read-only example and continue using `port-forward`.
