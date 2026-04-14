# Helm Go Template Summary

Helm charts use Go templates to turn values into Kubernetes manifests.

## Basic Idea

- files in `templates/` are not plain YAML, they are YAML plus template expressions;
- `values.yaml` provides input data;
- Helm renders the templates and produces plain Kubernetes manifests;
- the cluster never sees Go templates, only the rendered YAML.

## Most Common Expressions

Print a value:

```yaml
image: {{ .Values.image.repository }}:{{ .Values.image.tag }}
```

Use the release name:

```yaml
name: {{ .Release.Name }}
```

Use chart metadata:

```yaml
app.kubernetes.io/name: {{ .Chart.Name }}
```

## Pipelines

Pipelines pass the result of one expression into a function.

```yaml
name: {{ .Values.nameOverride | default .Chart.Name | trunc 63 | trimSuffix "-" }}
```

Typical functions used in charts:

- `default`: use a fallback value;
- `quote`: wrap a value in quotes;
- `upper` and `lower`: change case;
- `indent` and `nindent`: indent nested YAML correctly;
- `toYaml`: render maps or lists as YAML.

## Conditionals

Use `if` when a resource or block should only exist when enabled.

```yaml
{{- if .Values.ingress.enabled }}
apiVersion: networking.k8s.io/v1
kind: Ingress
...
{{- end }}
```

## Loops

Use `range` to repeat over a list or map.

```yaml
{{- range .Values.env }}
- name: {{ .name }}
  value: {{ .value | quote }}
{{- end }}
```

## Helper Templates

Helpers usually live in `_helpers.tpl`.
They avoid repeating naming and labeling logic.

Example:

```gotemplate
{{- define "demo-app.fullname" -}}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
```

Then use it from another template:

```yaml
name: {{ include "demo-app.fullname" . }}
```

## Accessing Important Objects

- `.Values`: values from `values.yaml` and `-f` files
- `.Release`: release metadata such as name and namespace
- `.Chart`: chart metadata
- `.Capabilities`: cluster capabilities detected by Helm
- `.Files`: extra files bundled in the chart

## Whitespace Control

`{{-` and `-}}` trim whitespace.
This matters because YAML is indentation-sensitive.

## Practical Rule for Students

- put structure in `templates/`;
- put things that change per environment in `values.yaml`;
- keep template logic simple;
- if a template starts looking like code, it is probably too complex.
