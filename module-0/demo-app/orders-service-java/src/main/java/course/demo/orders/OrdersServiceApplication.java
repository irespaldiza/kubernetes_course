package course.demo.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public final class OrdersServiceApplication {
    private static final Logger LOGGER = Logger.getLogger(OrdersServiceApplication.class.getName());
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(2))
        .build();

    private OrdersServiceApplication() {
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(getEnv("PORT", "8080"));
        String databaseUrl = System.getenv("DATABASE_URL");
        String catalogUrl = trimTrailingSlash(getEnv("CATALOG_URL", "http://catalog-service:8000"));

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/health", exchange -> {
            logRequest(exchange);
            writeJson(exchange, 200, Map.of("status", "ok", "service", "orders-service"));
        });
        server.createContext("/orders", new OrdersHandler(databaseUrl, catalogUrl));
        server.start();

        LOGGER.info("orders-service-java listening on :" + port);
    }

    private static String getEnv(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    private static String trimTrailingSlash(String value) {
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }

    private static void logRequest(HttpExchange exchange) {
        LOGGER.info(String.format(
            "request method=%s path=%s remote=%s",
            exchange.getRequestMethod(),
            exchange.getRequestURI().getPath(),
            exchange.getRemoteAddress()
        ));
    }

    private static void writeJson(HttpExchange exchange, int status, Object payload) throws IOException {
        byte[] response = OBJECT_MAPPER.writeValueAsBytes(payload);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }

    private static void writeText(HttpExchange exchange, int status, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }

    private static final class OrdersHandler implements HttpHandler {
        private final String databaseUrl;
        private final String catalogUrl;

        private OrdersHandler(String databaseUrl, String catalogUrl) {
            this.databaseUrl = databaseUrl;
            this.catalogUrl = catalogUrl;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            logRequest(exchange);
            switch (exchange.getRequestMethod()) {
                case "GET" -> handleListOrders(exchange);
                case "POST" -> handleCreateOrder(exchange);
                default -> exchange.sendResponseHeaders(405, -1);
            }
        }

        private void handleListOrders(HttpExchange exchange) throws IOException {
            if (databaseUrl == null || databaseUrl.isBlank()) {
                LOGGER.info("list_orders source=fallback reason=no_database");
                writeJson(exchange, 200, List.of(new Order(1, 1, "alice", "created")));
                return;
            }

            LOGGER.info("list_orders source=database");
            List<Order> orders = new ArrayList<>();
            try (Connection connection = openConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(
                     "SELECT id, product_id, customer, status FROM orders ORDER BY id"
                 )) {
                while (resultSet.next()) {
                    orders.add(new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getString("customer"),
                        resultSet.getString("status")
                    ));
                }
            } catch (Exception exception) {
                LOGGER.warning("list_orders error=" + exception.getMessage());
                writeText(exchange, 500, exception.getMessage());
                return;
            }

            LOGGER.info("list_orders result_count=" + orders.size());
            writeJson(exchange, 200, orders);
        }

        private void handleCreateOrder(HttpExchange exchange) throws IOException {
            OrderInput input;
            try (InputStream body = exchange.getRequestBody()) {
                input = OBJECT_MAPPER.readValue(body, OrderInput.class);
            } catch (Exception exception) {
                LOGGER.warning("create_order invalid_json error=" + exception.getMessage());
                writeText(exchange, 400, "invalid json");
                return;
            }

            if (input.productId() == null || input.productId() == 0 || isBlank(input.customer())) {
                LOGGER.warning(String.format(
                    "create_order validation_error=missing_fields product_id=%s customer=%s",
                    input.productId(),
                    input.customer()
                ));
                writeText(exchange, 400, "product_id and customer are required");
                return;
            }

            LOGGER.info(String.format(
                "create_order validating_product product_id=%d customer=%s",
                input.productId(),
                input.customer()
            ));

            try {
                validateProduct(input.productId(), catalogUrl);
            } catch (IllegalArgumentException exception) {
                LOGGER.warning("create_order validation_error=" + exception.getMessage());
                writeText(exchange, 400, exception.getMessage());
                return;
            } catch (Exception exception) {
                LOGGER.warning("create_order validation_error=" + exception.getMessage());
                writeText(exchange, 400, "catalog-service unavailable");
                return;
            }

            Order order = new Order(null, input.productId(), input.customer(), "created");

            if (databaseUrl == null || databaseUrl.isBlank()) {
                Order fallbackOrder = new Order(99, order.productId(), order.customer(), order.status());
                LOGGER.info(String.format(
                    "create_order source=fallback assigned_id=%d product_id=%d customer=%s",
                    fallbackOrder.id(),
                    fallbackOrder.productId(),
                    fallbackOrder.customer()
                ));
                writeJson(exchange, 201, fallbackOrder);
                return;
            }

            try (Connection connection = openConnection();
                 PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO orders (product_id, customer, status) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS
                 )) {
                statement.setInt(1, order.productId());
                statement.setString(2, order.customer());
                statement.setString(3, order.status());
                statement.executeUpdate();

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        order = new Order(keys.getInt(1), order.productId(), order.customer(), order.status());
                    }
                }
            } catch (Exception exception) {
                LOGGER.warning("create_order db_error=" + exception.getMessage());
                writeText(exchange, 500, exception.getMessage());
                return;
            }

            LOGGER.info(String.format(
                "create_order created id=%d product_id=%d customer=%s status=%s",
                order.id(),
                order.productId(),
                order.customer(),
                order.status()
            ));
            writeJson(exchange, 201, order);
        }

        private Connection openConnection() throws Exception {
            return DriverManager.getConnection(toJdbcUrl(databaseUrl));
        }

        private void validateProduct(int productId, String catalogUrl) throws Exception {
            String endpoint = catalogUrl + "/products";
            LOGGER.info("catalog_validation request_url=" + endpoint + " product_id=" + productId);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(2))
                .GET()
                .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IllegalArgumentException("catalog-service returned status " + response.statusCode());
            }

            List<Product> products = OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<Product>>() {});
            for (Product product : products) {
                if (Objects.equals(product.id(), productId)) {
                    LOGGER.info("catalog_validation success product_id=" + product.id() + " product_name=" + product.name());
                    return;
                }
            }

            LOGGER.info("catalog_validation unknown_product product_id=" + productId);
            throw new IllegalArgumentException("unknown product_id " + productId);
        }

        private String toJdbcUrl(String value) {
            if (value.startsWith("jdbc:postgresql://")) {
                return value;
            }

            URI uri = URI.create(value);
            String[] userInfo = uri.getUserInfo().split(":", 2);
            String username = userInfo[0];
            String password = userInfo.length > 1 ? userInfo[1] : "";
            String database = uri.getPath().replaceFirst("/", "");
            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 5432 : uri.getPort();

            Map<String, String> queryParameters = parseQueryParameters(uri.getQuery());
            queryParameters.remove("sslmode");
            queryParameters.put("user", username);
            queryParameters.put("password", password);

            StringBuilder builder = new StringBuilder();
            builder.append("jdbc:postgresql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(database);

            if (!queryParameters.isEmpty()) {
                builder.append("?");
                boolean first = true;
                for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                    if (!first) {
                        builder.append("&");
                    }
                    builder.append(entry.getKey()).append("=").append(entry.getValue());
                    first = false;
                }
            }

            return builder.toString();
        }

        private Map<String, String> parseQueryParameters(String query) {
            Map<String, String> parameters = new LinkedHashMap<>();
            if (query == null || query.isBlank()) {
                return parameters;
            }

            for (String part : query.split("&")) {
                String[] pieces = part.split("=", 2);
                if (pieces.length == 2) {
                    parameters.put(pieces[0], pieces[1]);
                }
            }

            return parameters;
        }

        private boolean isBlank(String value) {
            return value == null || value.isBlank();
        }
    }

    public record Order(
        Integer id,
        @JsonProperty("product_id") int productId,
        String customer,
        String status
    ) {
    }

    public record OrderInput(
        @JsonProperty("product_id") Integer productId,
        String customer
    ) {
    }

    public record Product(Integer id, String name, Double price) {
    }
}
