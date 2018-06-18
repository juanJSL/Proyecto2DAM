package com.example.jjsimon.proyectodam.EscuchaDeMensajes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.MainActivity;
import com.example.jjsimon.proyectodam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Este servicio se encarga de crear notificaciones cuando el usuario ha recibido
 * un mensaje y no se encuentra en la aplicacion
 */
public class ServicioNotificaciones extends Service {
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public ServicioNotificaciones() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        fire();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("receptorServicioParado");
        sendBroadcast(broadcastIntent);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void fire(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(FireBaseReferences.MENSAJES);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Mensaje mensaje = dataSnapshot.getValue(Mensaje.class);
                if(mensaje.getIdDestinatario().equals(idUser)) {
                    crearNotificacion();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void crearNotificacion() {
        int idNotificacion = 123456;
        NotificationManager notificationManager;
        //Instancio el notificactionManager
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Creo el patron de vibracion
        long [] vibrate = {0,100, 100 ,0,100};

        Intent i = new Intent(getBaseContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, i, 0);


        Notification.Builder builder = new Notification.Builder(getBaseContext())
                .setSmallIcon(R.drawable.ic_dialog_close_dark)
                .setContentTitle("Nuevo mensaje")
                .setContentText("Tienes un mensaje nuevo")
                .setVibrate(vibrate)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //Mando la notificacion
        notificationManager.notify(idNotificacion, builder.build());


    }
}
