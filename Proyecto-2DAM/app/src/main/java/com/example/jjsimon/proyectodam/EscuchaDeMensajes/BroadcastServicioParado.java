package com.example.jjsimon.proyectodam.EscuchaDeMensajes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Esta clase me permite iniciar de nuevo el servicio de notificaciones si ha sido detenido
 */
public class BroadcastServicioParado extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ServicioNotificaciones.class));;
    }
}
