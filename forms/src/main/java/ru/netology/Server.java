package ru.netology;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;

public class Server {
    public static final String GET= "GET";
    public static final String POST="POST";
    ExecutorService executorService;
    private List<List> handlers=new ArrayList();

    public Server(int numberOfThreads) {
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }
    private static Path getPath(String path) {
        final var filePath = Path.of(".", "public", path);
        return filePath;
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
        try (final var in = new BufferedReader(
             new InputStreamReader(socket.getInputStream()));
             final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            final var requestLine = in.readLine();
            final var parts = requestLine.split(" ");
            if (parts.length != 3) {
                return;
            }
            final var path = parts[1];
            if (path=="/messages") {
                
            }

            final var filePath = getPath(path);
            final var mimeType = Files.probeContentType(filePath);
            final var length = Files.size(filePath);

            Request request=new Request(parts[0],mimeType+" "+length," ");
            for(List handler:handlers){
                if (handler.contains(request.getMethod())){
                    Handler handler1= (Handler) handler.get(2);
                    handler1.handle(request,out);
                }
            }
            System.out.println(request.getQueryParams());
            request.getQueryParam("length");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(String method, String path, Handler handler){
    handlers.add(List.of(method,path,handler));
    }
}
