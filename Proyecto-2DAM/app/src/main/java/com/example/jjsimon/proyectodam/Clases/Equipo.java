package com.example.jjsimon.proyectodam.Clases;

/**
 * Esta clase reprsenta un equipode nuestra aplicacion
 * permite representarlo en la base de datos o recuperarlo de ella
 */
public class Equipo {
    private String idEquipo;
    private String nombre;
    private String ubicacion;//Primero latitud luego longitud
    private String descripcion;
    private String escudo;
    private String idAdmin;

    public Equipo() {

    }

    public Equipo(String idEquipo, String nombre, String ubicacion, String descripcion, String escudo) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.escudo = escudo;
    }

    public Equipo(String id_equipo, String nombre, String ubicacion, String descripcion, String escudo, String idAdmin) {
        this.idEquipo = id_equipo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.escudo = escudo;
        this.idAdmin = idAdmin;
    }

    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String id_equipo) {
        this.idEquipo = id_equipo;
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

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String id_admin) {
        this.idAdmin = idAdmin;
    }


    @Override
    public String toString() {
        return "Nombre: "+ this.nombre+
                "\nID: "+ this.idEquipo;
    }
}
