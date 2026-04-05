FROM golang:alpine AS builder

RUN apk add --no-cache git

COPY go.mod /app/
COPY whoami.go /app/
WORKDIR /app

RUN go mod tidy
RUN CGO_ENABLED=0 go build -o whoami

FROM alpine:latest

RUN adduser -D -u 10001 appuser

WORKDIR /app
COPY --from=builder /app/whoami /app/

USER appuser

EXPOSE 8080

CMD ["./whoami"]
