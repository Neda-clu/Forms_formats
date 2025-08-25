package ru.netology;


import java.io.BufferedOutputStream;

public class Main {
  public static void main(String[] args) {
    Server server = new Server(64);
    server.start(9999);

    server.addHandler("GET", "/messages", new Handler(){
          public void handle(Request request, BufferedOutputStream responseStream) {
              responseStream.write(("HTTP/1.1 200 OK\r\n"+
                      "Content:"+request.getHeaders+"\r\n"+
                      "Connection : close \r\n").getbytes());
              responseStream.flush
          }
      });


      server.addHandler("POST", "/messages", new Handler(){
          public void handle(Request request, BufferedOutputStream responseStream) {
              public void handle(Request request, BufferedOutputStream responseStream) {
                  responseStream.write(("HTTP/1.1 200 OK\r\n"+
                          "Content:"+request.getHeaders+"\r\n"+
                          "Connection : close \r\n").getbytes());
                  responseStream.flush
              });

      server.listen(9999);
  }
}