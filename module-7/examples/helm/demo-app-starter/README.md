# Demo App Helm Starter

This chart is the shared starting point for the `demo-app` Helm exercise.

It is intentionally close to the plain manifests in `currentState/` so students can focus on one main task:
move repeated or environment-specific data out of templates and into `values.yaml`.

The starter `values.yaml` already contains the structure students are expected to use, including the `database` block for the secret values and the `ingress` block for the optional ingress settings.

Students are not expected to know Go templates in advance.

Use this support file while working:

- `module-7/HELM_GO_TEMPLATE.md`

Expected student work:

- make resource names depend on the Helm release name;
- update internal references to use the rendered names;
- avoid hardcoding the namespace and use the release namespace;
- make images and tags configurable;
- make image pull policies configurable;
- make replica counts configurable;
- make ports configurable;
- make secret values configurable;
- make the Postgres persistence size configurable.
- make the ingress optional and configurable.

Recommended order:

1. Start with one easy replacement such as `replicas: 1`.
2. Move that value into `values.yaml`.
3. Replace the hardcoded value with `{{ .Values... }}`.
4. Run `helm template`.
5. Repeat with images and ports.
6. Change resource names to depend on the release and then fix internal references.
7. Remove hardcoded namespaces from templates.
8. Leave the starter `ConfigMap` templates as they are.
9. Finish with persistence and ingress options.

Useful patterns:

```yaml
replicas: {{ .Values.frontend.replicaCount }}
```

```yaml
image: "{{ .Values.frontend.image.repository }}:{{ .Values.frontend.image.tag }}"
```

```yaml
value: {{ .Values.catalogService.service.port | quote }}
```

```yaml
data:
  default.conf: |
{{ .Values.frontendNginxConfig | indent 4 }}
```

This starter is not the reference solution.
