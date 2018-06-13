package com.example.jjsimon.proyectodam.Clases;

/**
 * Created by diurno on 29/05/18.
 */

public class Conversacion {
    private String idConversacion;
    private String idU1;
    private String idU2;
    private String nickEmisor;

    public Conversacion() {
    }

    public Conversacion(String idConversacion, String idU1, String idU2, String nickEmisor) {
        this.idConversacion = idConversacion;
        this.idU1 = idU1;
        this.idU2 = idU2;
        this.nickEmisor = nickEmisor;
    }

    public String getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(String idConversacion) {
        this.idConversacion = idConversacion;
    }

    public String getIdU1() {
        return idU1;
    }

    public void setIdU1(String idU1) {
        this.idU1 = idU1;
    }

    public String getIdU2() {
        return idU2;
    }

    public void setIdU2(String idU2) {
        this.idU2 = idU2;
    }

    public String getNickEmisor() {
        return nickEmisor;
    }

    public void setNickEmisor(String nickEmisor) {
        this.nickEmisor = nickEmisor;
    }
}