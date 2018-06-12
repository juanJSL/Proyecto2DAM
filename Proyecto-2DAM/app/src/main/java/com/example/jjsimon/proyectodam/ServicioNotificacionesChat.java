package com.example.jjsimon.proyectodam;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Clase para crear el servicio que este a la escucha de nuevos mensajes en la BD y en el caso de que
 * haya un nuevo mensaje para el usuario crear una nueva notificacion
 */
public class ServicioNotificacionesChat extends IntentService {
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public ServicioNotificacionesChat() {
        super("ServicioNotificacionesChat");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            Log.w("SERVICIO", "onHandleIntent");
            database = FirebaseDatabase.getInstance();
            reference = database.getReference(FireBaseReferences.MENSAJES);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //Log.w("SERVICIO", "added");
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.w("SERVICIO", "change");
                    Mensaje m = dataSnapshot.getValue(Mensaje.class);
                    Log.w("SERVICIO", m.getCuerpoMensaje());
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
            //while(true);
    }




}
