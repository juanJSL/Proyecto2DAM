package com.example.jjsimon.proyectodam.Clases;

/**
 * Objeto que representa una replica se utilizar en una futura ampliacion de la aplicacion
 */
public class ALD {
    private int idAld;
    private String modelo;
    private String rol;
    private double fps;
    private Jugador jugadorPropietario;

    public ALD() {
    }

    public ALD(int idAld, String modelo, String rol, double fps) {
        this.idAld = idAld;
        this.modelo = modelo;
        this.rol = rol;
        this.fps = fps;
    }

    public int getIdAld() {
        return idAld;
    }

    public void setIdAld(int idAld) {
        this.idAld = idAld;
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

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public Jugador getJugadorPropietario() {
        return jugadorPropietario;
    }

    public void setJugadorPropietario(Jugador jugadorPropietario) {
        this.jugadorPropietario = jugadorPropietario;
    }
}
