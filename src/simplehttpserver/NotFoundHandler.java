package simplehttpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class NotFoundHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        String jsonString = "{ \"message\": \"Not Found\" }";
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Headers","x-prototype-version,x-requested-with");
        headers.add("Access-Control-Allow-Methods","GET,POST");
        headers.add("Access-Control-Allow-Origin","*");
        headers.add("Content-Type", "application/json");
        exchange.sendResponseHeaders(404, jsonString.length());
        outputStream.write(jsonString.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
