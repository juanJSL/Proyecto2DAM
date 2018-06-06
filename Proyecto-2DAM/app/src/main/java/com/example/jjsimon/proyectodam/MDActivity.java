package com.example.jjsimon.proyectodam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jjsimon.proyectodam.Clases.Conversacion;
import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterMensajes;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MDActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private DatabaseReference mensajesRef;
    private DatabaseReference conversacionesRef;
    private FirebaseDatabase database;
    private RecyclerViewAdapterMensajes adapter;
    private Button enviarBT;
    private EditText mensajeET;

    private String idEmisor;
    private String idDestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);

        //Obtengo los ID del emisor y el destinatario
        idEmisor = getIntent().getExtras().getString(ExtrasRef.ID_EMISOR);
        idDestinatario = getIntent().getExtras().getString(ExtrasRef.ID_DESTINATARIO);


        //Enlazo la recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.mensajes_recycler);
        mensajeET = findViewById(R.id.mensaje_et);
        enviarBT = findViewById(R.id.enviar_bt);


        //Instancio las referencias a la BD
        database = FirebaseDatabase.getInstance();
        conversacionesRef = database.getReference(FireBaseReferences.CONVERSACIONES);


        //Instancio el adaptador para la RecyclerView
        adapter = new RecyclerViewAdapterMensajes();
        //Indico el tipo layout para la RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Enlazo la RecyclerView con el Adaptador
        recyclerView.setAdapter(adapter);


        //Añado funcionalidad al boton de enviar
        enviarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mensajeET.getText().toString().equals(""))
                    enviarMsj();
            }
        });

        //Enlazo al adaptador un DataObserver para cuando un nuevo elemento sea insertado
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                //Muevo la Recycler al ultimo item
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }
        });

        //Realizo una consulta a la BD para recuperar los mensajes almacenados en la BD
        recuperarMensajes();
    }


    private void recuperarMensajes(){
        mensajesRef = FirebaseDatabase.getInstance().getReference(FireBaseReferences.MENSAJES);
        mensajesRef.orderByChild(FireBaseReferences.FECHA).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensaje mensaje = dataSnapshot.getValue(Mensaje.class);
                if(mensaje.getIdEmisor().equals(idEmisor) && mensaje.getIdDestinatario().equals(idDestinatario)
                        || mensaje.getIdEmisor().equals(idDestinatario) && mensaje.getIdDestinatario().equals(idEmisor))
                    adapter.addMensaje(mensaje);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    /**
     * Este metodo se ejecuta cuando se pulsa el boton enviar y se encarga de guardar el mensaje en la BD
     */
    private void enviarMsj() {
        //Creo el objeto mensaje
        Mensaje mensaje = new Mensaje();
        mensaje.setCuerpoMensaje(mensajeET.getText().toString());
        mensaje.setIdEmisor(getIntent().getExtras().getString(ExtrasRef.ID_EMISOR));
        mensaje.setIdDestinatario(getIntent().getExtras().getString(ExtrasRef.ID_DESTINATARIO));
        //mensaje.setFecha(Long.parseLong(ServerValue.TIMESTAMP.toString()));
        //mensaje.setIdConversacion(conversacion.getIdConversacion());

        //Guardo el mensaje en la BD
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.MENSAJES);
        reference = reference.push();
        mensaje.setIdMensaje(reference.getKey());
        reference.setValue(mensaje);

        //Añado la fecha
        reference.child(FireBaseReferences.FECHA).setValue(ServerValue.TIMESTAMP);

        //Limpio el editText
        mensajeET.setText("");
    }

}
