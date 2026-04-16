{{- define "demo-app.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "demo-app.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := include "demo-app.name" . -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{- define "demo-app.selectorLabels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/name: {{ include "demo-app.name" . }}
{{- end -}}

{{- define "demo-app.labels" -}}
{{ include "demo-app.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version | replace "+" "_" }}
{{- end -}}

{{- define "demo-app.frontend.fullname" -}}
{{- printf "%s-frontend" (include "demo-app.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "demo-app.catalog.fullname" -}}
{{- printf "%s-catalog-service" (include "demo-app.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "demo-app.orders.fullname" -}}
{{- printf "%s-orders-service" (include "demo-app.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "demo-app.postgres.fullname" -}}
{{- printf "%s-postgres" (include "demo-app.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "demo-app.databaseSecret.fullname" -}}
{{- printf "%s-database-secret" (include "demo-app.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "demo-app.frontendConfig.fullname" -}}
{{- printf "%s-frontend-nginx-config" (include "demo-app.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "demo-app.postgresInit.fullname" -}}
{{- printf "%s-postgres-init" (include "demo-app.fullname" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}
