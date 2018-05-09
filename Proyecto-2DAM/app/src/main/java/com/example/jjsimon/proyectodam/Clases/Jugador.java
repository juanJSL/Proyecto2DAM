package com.example.jjsimon.proyectodam.Clases;

import java.util.Date;

public class Jugador {
    private int idJugador;
    private String mail;
    private String nick;
    private String rol;
    private Date fnac;
    private String ciudad;
    private String fotoPerfil;
    private Equipo equipo;

    public Jugador() {
    }

    public Jugador(int idJugador, String mail, String nick, String rol, Date fnac, String ciudad, String fotoPerfil) {
        this.idJugador = idJugador;
        this.mail = mail;
        this.nick = nick;
        this.rol = rol;
        this.fnac = fnac;
        this.ciudad = ciudad;
        this.fotoPerfil = fotoPerfil;
    }

    public Jugador(String mail, String nick, String rol) {
        this.mail = mail;
        this.nick = nick;
        this.rol = rol;
    }


    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
