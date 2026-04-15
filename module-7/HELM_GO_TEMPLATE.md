# Helm Templates for Students

This guide is written for students who do not know Go templates yet.

The goal is not to learn all of Helm templating.
The goal is to make a plain Kubernetes YAML file configurable in a safe, small, repeatable way.

## Mental Model

Start from this idea:

- a file in `templates/` is normal YAML;
- some values inside that YAML are replaced by `{{ ... }}`;
- those expressions usually read data from `values.yaml`;
- `helm template` renders everything into plain Kubernetes manifests.

So students do not need to "write Go code".
They mostly need to replace hardcoded values with references to `.Values`.

## The Only Workflow You Need

When converting a manifest into a Helm template, use this order:

1. Start from working plain YAML.
2. Identify one hardcoded value that should become configurable.
3. Move that value into `values.yaml`.
4. Replace the hardcoded value in the template with `{{ .Values... }}`.
5. Run `helm template` and check the rendered YAML.
6. Repeat.

Do not try to template everything at once.

## Rule of Thumb

Keep this split:

- structure stays in `templates/`;
- values that may change stay in `values.yaml`.

Good candidates for `values.yaml`:

- image names;
- image tags;
- replica counts;
- ports;
- service type;
- secret values;
- `ConfigMap` content;
- persistence size.

Bad candidates for `values.yaml` in this module:

- full Kubernetes object structure;
- advanced conditionals;
- loops unless really necessary;
- complex helper logic.

## The Expressions Students Need Most

### Read one value

```yaml
replicas: {{ .Values.frontend.replicaCount }}
```

This means:
take `frontend.replicaCount` from `values.yaml` and print it there.

### Read a string

```yaml
image: "{{ .Values.frontend.image.repository }}:{{ .Values.frontend.image.tag }}"
```

### Quote a value

Use `quote` when the rendered value should clearly stay a string.

```yaml
value: {{ .Values.catalogService.service.port | quote }}
```

### Indent multiline text

This is very important for `ConfigMap` content.

```yaml
data:
  default.conf: |
{{ .Values.frontendNginxConfig | indent 4 }}
```

Without the correct indentation, the YAML breaks.

## The Three Helm Objects Students Need

### `.Values`

Reads from `values.yaml`.

```yaml
port: {{ .Values.ordersService.service.port }}
```

### `.Release`

Reads metadata from the Helm release.

```yaml
namespace: {{ .Release.Namespace }}
```

### `.Chart`

Reads metadata from `Chart.yaml`.

```yaml
app.kubernetes.io/name: {{ .Chart.Name }}
```

For this module, students mostly need `.Values` and sometimes `.Release.Namespace`.

## Before and After Examples

These examples are the important part.

### Example 1: Make `replicas` configurable

Plain YAML:

```yaml
spec:
  replicas: 1
```

Move the value into `values.yaml`:

```yaml
frontend:
  replicaCount: 1
```

Template version:

```yaml
spec:
  replicas: {{ .Values.frontend.replicaCount }}
```

### Example 2: Make the image configurable

Plain YAML:

```yaml
containers:
  - name: frontend
    image: module0/frontend:local
```

Move the values into `values.yaml`:

```yaml
frontend:
  image:
    repository: module0/frontend
    tag: local
```

Template version:

```yaml
containers:
  - name: frontend
    image: "{{ .Values.frontend.image.repository }}:{{ .Values.frontend.image.tag }}"
```

### Example 3: Make a port configurable

Plain YAML:

```yaml
ports:
  - containerPort: 8080
```

Move the value into `values.yaml`:

```yaml
ordersService:
  service:
    port: 8080
```

Template version:

```yaml
ports:
  - containerPort: {{ .Values.ordersService.service.port }}
```

### Example 4: Make an environment variable configurable

Plain YAML:

```yaml
env:
  - name: PORT
    value: "8000"
```

Move the value into `values.yaml`:

```yaml
catalogService:
  service:
    port: 8000
```

Template version:

```yaml
env:
  - name: PORT
    value: {{ .Values.catalogService.service.port | quote }}
```

### Example 5: Make a Secret value configurable

Plain YAML:

```yaml
stringData:
  POSTGRES_DB: appdb
  POSTGRES_USER: app
  POSTGRES_PASSWORD: app
```

Move the values into `values.yaml`:

```yaml
database:
  name: appdb
  user: app
  password: app
```

Template version:

```yaml
stringData:
  POSTGRES_DB: {{ .Values.database.name | quote }}
  POSTGRES_USER: {{ .Values.database.user | quote }}
  POSTGRES_PASSWORD: {{ .Values.database.password | quote }}
```

### Example 6: Make a `ConfigMap` body configurable

Plain YAML:

```yaml
data:
  init.sql: |
    CREATE TABLE test (
      id SERIAL PRIMARY KEY
    );
```

Move the text into `values.yaml`:

```yaml
postgresInitSql: |
  CREATE TABLE test (
    id SERIAL PRIMARY KEY
  );
```

Template version:

```yaml
data:
  init.sql: |
{{ .Values.postgresInitSql | indent 4 }}
```

This is the pattern students will need for both:

- the frontend Nginx config;
- the Postgres init SQL.

## Typical Conversion Pattern

If students are unsure what to do, use this recipe:

### Step 1

Take one hardcoded line:

```yaml
image: module0/catalog-service:local
```

### Step 2

Add the matching structure to `values.yaml`:

```yaml
catalogService:
  image:
    repository: module0/catalog-service
    tag: local
```

### Step 3

Replace the line in the template:

```yaml
image: "{{ .Values.catalogService.image.repository }}:{{ .Values.catalogService.image.tag }}"
```

### Step 4

Render and check:

```bash
helm template demo-app ./demo-app
```

If the output still shows a valid image string, the change is correct.

## What Students Do Not Need

For this module, students do not need:

- `range`;
- `if`;
- helper templates beyond reading them;
- `toYaml`;
- subcharts;
- advanced functions.

They can finish the exercise with simple value substitutions only.

## Recommended Order for the `demo-app` Exercise

If students feel blocked, use this order:

1. Make `frontend` image configurable.
2. Make `frontend` replica count configurable.
3. Make one service port configurable.
4. Repeat the same pattern for `catalog-service` and `orders-service`.
5. Move the database credentials into `values.yaml`.
6. Move `frontend-nginx-config` into `values.yaml`.
7. Move `postgres-init.sql` into `values.yaml`.
8. Move the Postgres persistence size into `values.yaml`.

This order goes from easiest to hardest.

## Validation Commands

Use these constantly while editing:

```bash
helm template demo-app ./demo-app
```

Renders the chart locally.

```bash
helm lint ./demo-app
```

Checks common chart problems.

## Common Mistakes

### YAML indentation breaks after adding `{{ ... }}`

Most common cause:
the expression was inserted with the wrong indentation level.

Check the rendered output with `helm template`.

### A string becomes a number

Use `quote`:

```yaml
value: {{ .Values.somePort | quote }}
```

### A multiline `ConfigMap` becomes invalid

Use:

```yaml
| 
{{ .Values.someText | indent 4 }}
```

and check the rendered YAML carefully.

### Students do not know where a value belongs

Ask:

"Is this part of the Kubernetes structure, or is it data that may change?"

If it may change, it probably belongs in `values.yaml`.

## Final Rule

If a student can explain the template as:

"This is the same YAML as before, but with a few values read from `values.yaml`"

then the chart is at the right level for this module.
