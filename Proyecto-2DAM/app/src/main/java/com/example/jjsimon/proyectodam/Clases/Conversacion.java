package com.example.jjsimon.proyectodam.Clases;

/**
 * Created by diurno on 29/05/18.
 */

public class Conversacion {
    private String idConversacion;
    private String idEmisor;
    private String idRemitente;
    private String nickEmisor;

    public Conversacion() {
    }

    public Conversacion(String idConversacion, String idEmisor, String idRemitente, String nickEmisor) {
        this.idConversacion = idConversacion;
        this.idEmisor = idEmisor;
        this.idRemitente = idRemitente;
        this.nickEmisor = nickEmisor;
    }

    public String getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(String idConversacion) {
        this.idConversacion = idConversacion;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
    }

    public String getNickEmisor() {
        return nickEmisor;
    }

    public void setNickEmisor(String nickEmisor) {
        this.nickEmisor = nickEmisor;
    }
}