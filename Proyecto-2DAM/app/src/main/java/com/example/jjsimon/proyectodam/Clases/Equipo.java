package com.example.jjsimon.proyectodam.Clases;

public class Equipo {
    private String id_equipo;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private String escudo;
    private String id_admin;

    public Equipo() {

    }

    public Equipo(String idEquipo, String nombre, String ubicacion, String descripcion, String escudo) {
        this.id_equipo = idEquipo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.escudo = escudo;
    }

    public Equipo(String id_equipo, String nombre, String ubicacion, String descripcion, String escudo, String id_admin) {
        this.id_equipo = id_equipo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.escudo = escudo;
        this.id_admin = id_admin;
    }

    public String getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(String id_equipo) {
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

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }
}
