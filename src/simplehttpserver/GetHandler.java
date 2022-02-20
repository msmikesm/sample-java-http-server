package simplehttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Arrays.copyOf;

public class GetHandler implements HttpHandler {
    private NotFoundHandler notFoundHandler;
    GetHandler() {
        this.notFoundHandler = new NotFoundHandler();
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equals(exchange.getRequestMethod())) {
            String requestParamValue = this.handleGetRequest(exchange);
            this.handleResponse(exchange, requestParamValue);
        } else {
            this.notFoundHandler.handle(exchange);
        }
    }

    private String handleGetRequest(HttpExchange exchange) {
        String requestUri = exchange.getRequestURI().toString();
        String[] queries = requestUri.toString().split("\\?");
        if (queries.length >= 1) {
            String[] queryParams = queries[1].split("&");
            return Arrays.toString(queryParams);
        }
        return "";
    }

    private void handleResponse(HttpExchange exchange, String reqParamValue) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        String jsonString = "{ \"status\": \"ok\", \"keys\": \"%s\" }".formatted(reqParamValue);
        System.out.println(jsonString + " - Message");
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
        headers.add("Access-Control-Allow-Methods","GET,POST");
        headers.add("Access-Control-Allow-Origin","*");
        headers.add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonString.length());
        outputStream.write(jsonString.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
