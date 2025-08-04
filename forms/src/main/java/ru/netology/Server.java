package ru.netology;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

public class Server {
    final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png",
            "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html",
            "/events.html", "/events.js");
    ExecutorService executorService;



    public Server(int numberOfThreads) {
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void start(int port) {
        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
             final var socket = serverSocket.accept();
                executorService.execute(() -> connectionProcessing(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void connectionProcessing(Socket socket) {
        try (final var in = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
             final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            final var requestLine = in.readLine();
            final var parts = requestLine.split(" ");
            if (parts.length != 3) {
                return;
            }
            final var path = parts[1];
            if (!validPaths.contains(path)) {
                out.write((""
                ).getBytes());
                out.flush();
                return;
            }
            final var filePath = getPath(path);
            final var mimeType = Files.probeContentType(filePath);
            if (path.equals("/classic.html")) {
                final var template = Files.readString(filePath);
                final var content = template.replace(
                        "", "").getBytes();
                out.write((
                        "").getBytes());
                out.write(content);
                out.flush();
                return;
            }
            final var length = Files.size(filePath);
            out.write((
                    "").getBytes());
            Files.copy(filePath, out);
            out.flush();

            Request request=new Request(path);
            request.setParams(mimeType+" "+length);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getPath(String path) {
        final var filePath = Path.of(".", "public", path);
        return filePath;
    }
}