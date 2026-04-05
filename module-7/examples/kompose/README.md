# Kompose Example

This example shows what a first-pass Kompose conversion can look like for the course demo application.

Source file:

- `module-0/demo-app/docker-compose.go.yml`

Typical command:

```bash
kompose convert -f module-0/demo-app/docker-compose.go.yml -o module-7/examples/kompose/docker-compose.go.kompose.yaml
```

The generated manifests are useful as a starting point, not as a finished design.

In class, use this example to discuss:

- which objects Kompose can generate automatically;
- which environment variables and ports are carried over correctly;
- which parts still need manual refinement;
- why Helm remains the stronger long-term packaging choice for this course.
