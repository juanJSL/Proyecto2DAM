package com.example.jjsimon.proyectodam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterMensajes;

import java.util.ArrayList;

public class MDActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Mensaje> mensajesList;
    private RecyclerViewAdapterMensajes adapter;

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

    }

    private void inicializarLista() {
        Log.w("BUCLE ", "Entro en el bucle para inicializar la lista");
        Mensaje m1 = new Mensaje("id1", "idConversacion1", "Mensaje1", "fecha", Mensaje.ENVIADO);
        Mensaje m2 = new Mensaje("id2", "idConversacion2", "Mensaje2", "fecha", Mensaje.ENVIADO);
        Mensaje m3 = new Mensaje("id3", "idConversacion3", "Mensaje3", "fecha", Mensaje.ENVIADO);
        Mensaje m4 = new Mensaje("id4", "idConversacion4", "Mensaje4", "fecha", Mensaje.ENVIADO);
        Mensaje m5 = new Mensaje("id5", "idConversacion5", "Mensaje5", "fecha", Mensaje.RECIBIDO);
        Mensaje m6 = new Mensaje("id6", "idConversacion6", "Mensaje6", "fecha", Mensaje.RECIBIDO);
        Mensaje m7 = new Mensaje("id7", "idConversacion7", "Mensaje7", "fecha", Mensaje.RECIBIDO);
        Mensaje m8 = new Mensaje("id8", "idConversacion8", "Mensaje8", "fecha", Mensaje.RECIBIDO);

        Mensaje [] array = {m1, m2, m3, m4, m5, m6 ,m7 ,m8};

        for (Mensaje conversacion: array) {
            mensajesList.add(conversacion);
        }
    }


}
