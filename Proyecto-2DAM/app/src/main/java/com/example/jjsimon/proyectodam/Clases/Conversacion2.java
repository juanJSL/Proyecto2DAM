package com.example.jjsimon.proyectodam.Clases;

/**
 * Created by JJ on 13/06/2018.
 */

public class Conversacion2 {
    private String idConversacion;
    private String idEmisor;
    private String idDestinatario;
    private boolean tieneMensajes;

    public Conversacion2() {
    }

    public Conversacion2(String idConversacion, String idEmisor, String idDestinatario, boolean tieneMensajes) {
        this.idConversacion = idConversacion;
        this.idEmisor = idEmisor;
        this.idDestinatario = idDestinatario;
        this.tieneMensajes = tieneMensajes;
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

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public boolean isTieneMensajes() {
        return tieneMensajes;
    }

    public void setTieneMensajes(boolean tieneMensajes) {
        this.tieneMensajes = tieneMensajes;
    }
}
