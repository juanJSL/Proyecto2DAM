package com.example.jjsimon.proyectodam.Clases;

public class Mensaje {

    public static int ENVIADO = 0;
    public static int RECIBIDO = 1;
    public static int ENVIO_SOLICITUD = 2;
    public static int RESPUESTA_SOLICITUD = 3;


    private String idMensaje;
    private String idConversacion;
    private String cuerpoMensaje;
    private long fecha;
    private String idDestinatario;
    private String idEmisor;
    private int tipoMensaje;

    public Mensaje() {
    }

    public Mensaje(String idMensaje, String idConversacion, String cuerpoMensaje, long fecha, String idDestinatario, String idEmisor, int tipoMensaje) {
        this.idMensaje = idMensaje;
        this.idConversacion = idConversacion;
        this.cuerpoMensaje = cuerpoMensaje;
        this.fecha = fecha;
        this.idDestinatario = idDestinatario;
        this.idEmisor = idEmisor;
        this.tipoMensaje = tipoMensaje;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(String idConversacion) {
        this.idConversacion = idConversacion;
    }

    public String getCuerpoMensaje() {
        return cuerpoMensaje;
    }

    public void setCuerpoMensaje(String cuerpoMensaje) {
        this.cuerpoMensaje = cuerpoMensaje;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(String idEmisor) {
        this.idEmisor = idEmisor;
    }

    public int getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(int tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
}