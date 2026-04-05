FROM golang:1.22-alpine AS build

WORKDIR /src

COPY go.mod go.sum ./
RUN go mod download

COPY . .
RUN CGO_ENABLED=0 GOOS=linux go build -o /bin/orders-service ./cmd/orders-service

FROM alpine:3.20

WORKDIR /app
COPY --from=build /bin/orders-service /app/orders-service

ENV PORT=8080

EXPOSE 8080

CMD ["/app/orders-service"]
