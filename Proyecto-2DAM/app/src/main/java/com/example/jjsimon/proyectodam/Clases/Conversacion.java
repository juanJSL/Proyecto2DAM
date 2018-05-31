package com.example.jjsimon.proyectodam.Clases;

/**
 * Created by diurno on 29/05/18.
 */

public class Conversacion {
    private String idConversacion;
    private String idEmisor;
    private String idReceptor;
    private String nickEmisor;

    public Conversacion() {
    }

    public Conversacion(String idConversacion, String idEmisor, String idReceptor, String nickEmisor) {
        this.idConversacion = idConversacion;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
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

    public String getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getNickEmisor() {
        return nickEmisor;
    }

    public void setNickEmisor(String nickEmisor) {
        this.nickEmisor = nickEmisor;
    }
}
