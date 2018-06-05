package com.example.jjsimon.proyectodam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    //private ArrayList<Mensaje> mensajesList;
    private RecyclerViewAdapterMensajes adapter;
    private Button enviarBT;
    private EditText mensajeET;
    private Conversacion conversacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);

        //Enlazo la recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.mensajes_recycler);

        //mensajesList = new ArrayList<>();

        //adapter = new RecyclerViewAdapterMensajes(mensajesList);
        adapter = new RecyclerViewAdapterMensajes();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                anadirListener();
            }
        });

        //Cargar datos
        consultarConversacion();
        //inicializarLista();
        //Añadir listener a la BD para los mensajes
        //anadirListener();



        //Enlazo el boton y le añado la funcionalidad
        mensajeET = findViewById(R.id.mensaje_et);
        enviarBT = findViewById(R.id.enviar_bt);
        enviarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mensajeET.getText().toString().equals(""))
                    enviarMsj();
            }
        });

    }

    /**
     * Este metodo realiza una consulta a la base de datos para obtener la conversacion
     */
    private void consultarConversacion() {
        final String idUuser = getIntent().getExtras().getString(ExtrasRef.ID_EMISOR);
        final String idDestinatario = getIntent().getExtras().getString(ExtrasRef.ID_DESTINATARIO);


        
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.w("CONSULTA", "ADDED");
                Conversacion c = dataSnapshot.getValue(Conversacion.class);
                if(idUuser.equals(c.getIdU1()) && idDestinatario.equals(c.getIdU2())
                        || idUuser.equals(c.getIdU2()) && idDestinatario.equals(c.getIdU1())) {
                    recuperarMensajes((conversacion = c));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.w("CONSULTA", "CHANGED");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.w("CONSULTA", "REMOVED");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.w("CONSULTA", "MOVED");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //Referencia de la base de datos apuntando al nodo conversaciones
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.CONVERSACIONES);
        
        //Realizo una consulta obteniendo
        reference.orderByChild(FireBaseReferences.ID_U1).equalTo(idUuser).addChildEventListener(childEventListener);
        reference.orderByChild(FireBaseReferences.ID_U2).equalTo(idUuser).addChildEventListener(childEventListener);
        //reference.orderByChild(FireBaseReferences.ID_U1).equalTo(idUuser).addListenerForSingleValueEvent(valueEventListener);
        //reference.orderByChild(FireBaseReferences.ID_U2).equalTo(idUuser).addListenerForSingleValueEvent(valueEventListener);

    }


    private void recuperarMensajes(final Conversacion conversacion){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.MENSAJES);
        reference.orderByChild(FireBaseReferences.FECHA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //mensajesList.removeAll(mensajesList);
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Mensaje mensaje = snapshot.getValue(Mensaje.class);
                    Log.w("RECUPERAR_MSJ", ""+ mensaje.getIdConversacion());
                    Log.w("RECUPERAR_MSJ", ""+ conversacion.getIdConversacion());
                    Log.w("RECUPERAR_MSJ", ""+ mensaje.getCuerpoMensaje());
                    Log.w("RECUPERAR_MSJ", ""+ mensaje.getIdEmisor());
                    Log.w("RECUPERAR_MSJ", ""+ mensaje.getIdDestinatario());
                    if(mensaje.getIdConversacion().equals(conversacion.getIdConversacion()))
                        adapter.addMensaje(mensaje);
                        //mensajesList.add(mensaje);
                }
                anadirListener();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void anadirListener() {
        int pos = adapter.getItemCount();
        Log.w("posss", pos+"");
        recyclerView.scrollToPosition(1);
    }



    private void enviarMsj() {
        //Creo el objeto mensaje
        Mensaje mensaje = new Mensaje();
        mensaje.setCuerpoMensaje(mensajeET.getText().toString());
        mensaje.setIdEmisor(getIntent().getExtras().getString(ExtrasRef.ID_EMISOR));
        mensaje.setIdDestinatario(getIntent().getExtras().getString(ExtrasRef.ID_DESTINATARIO));
        mensaje.setFecha(ServerValue.TIMESTAMP.toString());
        mensaje.setIdConversacion(conversacion.getIdConversacion());

        //Guardo el mensaje en la BD
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.MENSAJES);
        reference = reference.push();
        mensaje.setIdMensaje(reference.getKey());
        reference.setValue(mensaje);

        //Añado la fecha
        //reference.child(FireBaseReferences.FECHA).setValue(ServerValue.TIMESTAMP);
        reference.child(FireBaseReferences.FECHA).setValue("4a");
        adapter.addMensaje(mensaje);
        //mensajesList.add(mensaje);
        adapter.notifyDataSetChanged();
        mensajeET.setText("");
    }
}
