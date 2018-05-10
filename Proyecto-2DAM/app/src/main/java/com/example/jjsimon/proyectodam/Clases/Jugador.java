package com.example.jjsimon.proyectodam.Clases;

import java.util.Date;

public class Jugador {
    private String idJugador;
    private String mail;
    private String nick;
    private String rol;
    private String fotoPerfil;
    private Equipo equipo;

    public Jugador() {
    }

    public Jugador(String idJugador, String mail, String nick, String rol, String fotoPerfil) {
        this.idJugador = idJugador;
        this.mail = mail;
        this.nick = nick;
        this.rol = rol;
        this.fotoPerfil = fotoPerfil;
    }

    public Jugador(String mail, String nick, String rol) {
        this.mail = mail;
        this.nick = nick;
        this.rol = rol;
    }


    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
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
