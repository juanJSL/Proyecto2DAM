package com.example.jjsimon.proyectodam.Clases;

public class Mensaje {
    public static int ENVIADO = 0;
    public static int RECIBIDO = 1;
    public static int ENVIO_SOLICITUD = 2;
    public static int RESPUESTA_SOLICITUD = 3;

    private String idMensaje;
    private String idConversacion;
    private String cuerpoMensaje;
    private String fecha;
    private int tipoMensaje;

    public Mensaje() {
    }

    public Mensaje(String idMensaje, String idConversacion, String cuerpoMensaje, String fecha, int tipoMensaje) {
        this.idMensaje = idMensaje;
        this.idConversacion = idConversacion;
        this.cuerpoMensaje = cuerpoMensaje;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(int tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
}