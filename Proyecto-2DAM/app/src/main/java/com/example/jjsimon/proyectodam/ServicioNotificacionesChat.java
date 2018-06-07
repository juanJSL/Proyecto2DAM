package com.example.jjsimon.proyectodam;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * Clase para crear el servicio que este a la escucha de nuevos mensajes en la BD y en el caso de que
 * haya un nuevo mensaje para el usuario crear una nueva notificacion
 */
public class ServicioNotificacionesChat extends IntentService {


    public ServicioNotificacionesChat() {
        super("ServicioNotificacionesChat");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
