package com.example.jjsimon.proyectodam.Clases;

public class ALD {
    private int idAld;
    private String marca;
    private String modelo;
    private String rol;
    private double fos;
    private Jugador jugadorPropietario;

    public ALD() {
    }

    public ALD(int idAld, String marca, String modelo, String rol, double fos) {
        this.idAld = idAld;
        this.marca = marca;
        this.modelo = modelo;
        this.rol = rol;
        this.fos = fos;
    }

    public int getIdAld() {
        return idAld;
    }

    public void setIdAld(int idAld) {
        this.idAld = idAld;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public double getFos() {
        return fos;
    }

    public void setFos(double fos) {
        this.fos = fos;
    }

    public Jugador getJugadorPropietario() {
        return jugadorPropietario;
    }

    public void setJugadorPropietario(Jugador jugadorPropietario) {
        this.jugadorPropietario = jugadorPropietario;
    }
}
