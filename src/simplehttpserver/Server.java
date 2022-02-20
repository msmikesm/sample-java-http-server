package simplehttpserver;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // handlers
        Server.mainHandler(server);
        Server.pingHandler(server);

        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server started on port 8000");
    }

    private static void mainHandler(HttpServer server) {
        server.createContext("/", new GetHandler());
    }

    private static void pingHandler(HttpServer server) {
        server.createContext("/ping", new GetHandler());
    }
}
