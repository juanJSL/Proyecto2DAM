package com.example.jjsimon.proyectodam.Clases;

public class Equipo2 {
    private int id_equipo;
    private int id_admin;
    private String escudo;
    private String nombre;
    private String ubicacion;

    public Equipo2() {
    }

    public Equipo2(int id_equipo, int id_admin, String escudo, String nombre, String ubicacion) {
        this.id_equipo = id_equipo;
        this.id_admin = id_admin;
        this.escudo = escudo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
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
}
