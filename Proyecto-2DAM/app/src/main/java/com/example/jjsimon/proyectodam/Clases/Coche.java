package com.example.jjsimon.proyectodam.Clases;

public class Coche {
    private String dueno;
    private String marca;

    public Coche() {
    }

    public Coche(String dueno, String marca) {
        this.dueno = dueno;
        this.marca = marca;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
