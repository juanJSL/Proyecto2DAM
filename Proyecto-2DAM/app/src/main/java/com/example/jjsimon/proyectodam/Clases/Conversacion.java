package com.example.jjsimon.proyectodam.Clases;

/**
 * Esta clase representa una conversacion en la base de datos
 */
public class Conversacion {
    private String idConversacion;
    private String idEmisor;
    private String idDestinatario;
    private boolean tieneMensajes;

    public Conversacion() {
    }

    public Conversacion(String idConversacion, String idEmisor, String idDestinatario, boolean tieneMensajes) {
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

    public boolean getTieneMensajes() {
        return tieneMensajes;
    }

    public void setTieneMensajes(boolean tieneMensajes) {
        this.tieneMensajes = tieneMensajes;
    }



    @Override
    public boolean equals(Object obj) {
        try {
            Conversacion c = (Conversacion) obj;
            return this.getIdConversacion().equals(c.getIdConversacion());
        }catch (ClassCastException ex){
            ex.printStackTrace();
            return false;
        }
    }
}