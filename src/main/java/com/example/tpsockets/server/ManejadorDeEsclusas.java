package com.example.tpsockets.server;

import java.io.*;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.example.tpsockets.server.EsclusaServer.otorgarAutorizacion;

public class ManejadorDeEsclusas {
    private Queue<Socket> barcosDelOeste = new ConcurrentLinkedQueue<>();
    private Queue<Socket> barcosDelEste = new ConcurrentLinkedQueue<>();
    public ManejadorDeEsclusas() {}
    public void handle(Socket socket) {
        try {
            if (this.parseOrientacion(socket.getInputStream()).equals("OESTE")) {
                this.barcosDelOeste.add(socket);
                System.out.println("Un barco entró a la esclusa OESTE. Barcos en esclusa OESTE: " + barcosDelOeste.size());
                this.evaluarAcceso(barcosDelOeste,"OESTE");
            } else {
                this.barcosDelEste.add(socket);
                System.out.println("Un barco entró a la esclusa ESTE. Barcos en esclusa ESTE: " + barcosDelEste.size());
                this.evaluarAcceso(barcosDelEste,"ESTE");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void evaluarAcceso(Queue<Socket> barcos, String esclusa) throws IOException {
        System.out.println(barcos.size() +" "+esclusa);
        if (barcos.size() == 2)
            darAcceso(barcos,esclusa);

    }

    private void darAcceso(Queue<Socket> barcos,String esclusa) throws IOException {
        System.out.println("Estan pasando barcos de la esclusa "+ esclusa);
        Socket barco = barcos.poll();
        Socket barco2 = barcos.poll();
        otorgarAutorizacion(barco);
        otorgarAutorizacion(barco2);

    }

    private String parseOrientacion(InputStream inputStream){
        try {
            String orientacion = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            orientacion = reader.readLine();
            return orientacion.split("/")[1].trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}