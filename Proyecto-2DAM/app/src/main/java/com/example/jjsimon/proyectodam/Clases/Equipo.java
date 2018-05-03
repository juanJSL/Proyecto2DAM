package com.example.jjsimon.proyectodam.Clases;

public class Equipo {
    private int id_equipo;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private String escudo;
    private Jugador jugadorAdministrador;

    public Equipo() {

    }

    public Equipo(int idEquipo, String nombre, String ubicacion, String descripcion, String escudo) {
        this.id_equipo = idEquipo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.escudo = escudo;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
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
