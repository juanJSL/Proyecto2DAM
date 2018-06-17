package com.example.jjsimon.proyectodam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CapturasCodigo {

    private void aaa(){

        //Referencia a la base de datos apuntando al nodo jugadores
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("jugadores");
        //Añado el agente de escucha
        reference.child("idJugador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Casteo el dataSnapshot que contiene el jugador
                Jugador j = dataSnapshot.getValue(Jugador.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }


}
