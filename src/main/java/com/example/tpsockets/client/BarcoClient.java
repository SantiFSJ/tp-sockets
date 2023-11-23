package com.example.tpsockets.client;

import com.example.tpsockets.client.model.Barco;

import java.io.*;
import java.net.Socket;

public class BarcoClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1066;

    public static void main(String[] args) {
        try {
            int cantidadBarcos = 20;
            for (int i = 0; i < cantidadBarcos; i++) {
                String orientation = (i % 2 == 1) ? "OESTE" : "ESTE";
                int shipNumber = i;
                new Thread(() -> cruzarEstrecho(new Barco(shipNumber, orientation))).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cruzarEstrecho(Barco barco) {
        try {
            Socket socket = new Socket(HOST, PORT);
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(barco.getNumeroDeBarco()+"/"+barco.getOrientacion());
            System.out.println("El Barco nro"+barco.getNumeroDeBarco()+" con orientación "+barco.getOrientacion()+" esta esperando otro barco para poder cruzar");

            if(sePermitioLaNavegacion(socket))
                System.out.println("El barco número " + barco.getNumeroDeBarco() + " pudo cruzar el estrecho hacia el "+barco.getOrientacion()+" con éxito!!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean sePermitioLaNavegacion(Socket socket){
        try {
            String line = "";
            InputStream input = null;
            input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            line = reader.readLine();
            return line.equals("OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}