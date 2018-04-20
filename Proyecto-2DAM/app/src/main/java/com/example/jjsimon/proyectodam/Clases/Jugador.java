package com.example.jjsimon.proyectodam.Clases;

import java.util.Date;

public class Jugador {
    private int idJugador;
    private String nombre;
    private String nick;
    private String rol;
    private Date fnac;
    private String ciudad;
    private String fotoPerfil;
    private Equipo equipo;

    public Jugador() {
    }

    public Jugador(int idJugador, String nombre, String nick, String rol, Date fnac, String ciudad, String fotoPerfil) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.nick = nick;
        this.rol = rol;
        this.fnac = fnac;
        this.ciudad = ciudad;
        this.fotoPerfil = fotoPerfil;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getFnac() {
        return fnac;
    }

    public void setFnac(Date fnac) {
        this.fnac = fnac;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
