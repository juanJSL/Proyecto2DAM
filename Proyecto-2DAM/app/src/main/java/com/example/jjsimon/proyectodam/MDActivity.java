package com.example.jjsimon.proyectodam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterMensajes;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MDActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Mensaje> mensajesList;
    private RecyclerViewAdapterMensajes adapter;
    private Button enviarBT;
    private EditText mensajeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);

        //Enlazo la recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.mensajes_recycler);

        mensajesList = new ArrayList<>();

        //Cargar datos
        inicializarLista();

        adapter = new RecyclerViewAdapterMensajes(mensajesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        //Enlazo el boton y le añado la funcionalidad
        mensajeET = findViewById(R.id.mensaje_et);
        enviarBT = findViewById(R.id.enviar_bt);
        enviarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMsj();
            }
        });

    }

    private void enviarMsj() {
        Mensaje mensaje = new Mensaje();
        mensaje.setCuerpoMensaje(mensajeET.getText().toString());
        mensaje.setIdEmisor(getIntent().getExtras().getString(ExtrasRef.ID_EMISOR));
        mensaje.setIdDestinatario(getIntent().getExtras().getString(ExtrasRef.ID_DESTINATARIO));
        mensaje.setFecha(ServerValue.TIMESTAMP.toString());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.MENSAJES);

        reference = reference.push();

        mensaje.setIdMensaje(reference.getKey());

        reference.setValue(mensaje);

        reference.child(FireBaseReferences.FECHA).setValue(ServerValue.TIMESTAMP);

        mensajeET.setText("");
    }



    private void inicializarLista() {
        Mensaje m1 = new Mensaje("id1", "conversacion", "Mensaje1", "fecha", "destinatario", "emisor", Mensaje.ENVIADO);
        Mensaje m2 = new Mensaje("id2", "conversacion", "Mensaje2", "fecha", "destinatario", "emisor", Mensaje.ENVIADO);
        Mensaje m3 = new Mensaje("id3", "conversacion", "Mensaje3", "fecha", "destinatario", "emisor", Mensaje.ENVIADO);
        Mensaje m4 = new Mensaje("id4", "conversacion", "Mensaje4", "fecha", "destinatario", "emisor", Mensaje.ENVIADO);
        Mensaje m5 = new Mensaje("id5", "conversacion", "Mensaje5", "fecha", "destinatario", "emisor", Mensaje.RECIBIDO);
        Mensaje m6 = new Mensaje("id6", "conversacion", "Mensaje6", "fecha", "destinatario", "emisor", Mensaje.RECIBIDO);
        Mensaje m7 = new Mensaje("id7", "conversacion", "Mensaje7", "fecha", "destinatario", "emisor", Mensaje.RECIBIDO);
        Mensaje m8 = new Mensaje("id8", "conversacion", "Mensaje8", "fecha", "destinatario", "emisor", Mensaje.RECIBIDO);

        Mensaje [] array = {m1, m2, m3, m4, m5, m6 ,m7 ,m8};

        for (Mensaje conversacion: array) {
            mensajesList.add(conversacion);
        }
    }


}
