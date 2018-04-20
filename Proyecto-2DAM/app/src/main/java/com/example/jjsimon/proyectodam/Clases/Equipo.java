package com.example.jjsimon.proyectodam.Clases;

public class Equipo {
    private int idEquipo;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private String escudo;
    private Jugador jugadorAdministrador;

    public Equipo() {

    }

    public Equipo(int idEquipo, String nombre, String ubicacion, String descripcion, String escudo) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.escudo = escudo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    public Jugador getJugadorAdministrador() {
        return jugadorAdministrador;
    }

    public void setJugadorAdministrador(Jugador jugadorAdministrador) {
        this.jugadorAdministrador = jugadorAdministrador;
    }
}
