package com.example.tpsockets.client.model;

public class Barco{
    private int numeroDeBarco;
    private String orientacion;

    public Barco(int numeroDeBarco, String orientacion) {
        this.numeroDeBarco = numeroDeBarco;
        this.orientacion = orientacion;

    }

    public int getNumeroDeBarco() {
        return numeroDeBarco;
    }

    public String getOrientacion() {
        return orientacion;
    }

}
