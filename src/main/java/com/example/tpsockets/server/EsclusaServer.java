package com.example.tpsockets.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EsclusaServer {
    private static final int PORT = 1066;
    private static ManejadorDeEsclusas exclusaHandler = new ManejadorDeEsclusas();

    public static void main(String[] args) {
        try (var server = new ServerSocket(PORT)) {
            System.out.println("El servidor de esclusas esta listo para recibir solicitudes");
            while(true) {
                Socket socket = server.accept();
                exclusaHandler.handle(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void otorgarAutorizacion(Socket barco) throws IOException {
        OutputStream output = barco.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("OK");

    }

}