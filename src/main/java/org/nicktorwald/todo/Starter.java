package org.nicktorwald.todo;

import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.nicktorwald.todo.config.Application;

public class Starter {

    public static final String BASE_URI = "http://0.0.0.0:8181/api/";

    public static void main(String[] args) {
        HttpServer serverInstance = startServer();
        doWhenShutdown(serverInstance::shutdown);
    }

    private static HttpServer startServer() {
        return GrizzlyHttpServerFactory
                .createHttpServer(URI.create(BASE_URI), new Application());
    }

    private static void doWhenShutdown(Runnable action) {
        Runtime.getRuntime().addShutdownHook(new Thread(action));
    }

}
