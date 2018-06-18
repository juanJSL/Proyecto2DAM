package com.example.jjsimon.proyectodam;

import android.nfc.Tag;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.jjsimon.proyectodam.Clases.Conversacion;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterMensajes;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

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
    private boolean emisortTieneConverascion;
    private boolean destinatarioTieneConverascion;

    private Conversacion conversacionEmisor;
    private Conversacion conversacionDestinatario;

    private Query tieneConversacionQuery;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);

        //Obtengo los ID del emisor y el destinatario
        idEmisor = getIntent().getExtras().getString(ExtrasRef.ID_EMISOR);
        idDestinatario = getIntent().getExtras().getString(ExtrasRef.ID_DESTINATARIO);


        //Cambio el nombre del action bar
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.JUGADORES);
        reference.child(idDestinatario)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Jugador j = dataSnapshot.getValue(Jugador.class);
                        getSupportActionBar().setTitle(j.getNick());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        //Consulto la conversacion del usuario
        comprobarConversacion(idEmisor, idDestinatario);
        //Consulto la conversacion del destinatario
        comprobarConversacion(idDestinatario, idEmisor);


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

        //Enlazo al adaptador un DataObserver para
        // cuando un nuevo elemento sea insertado
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                //Muevo la Recycler al ultimo item
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }
        });

        //Realizo una consulta a la BD para recuperar
        // los mensajes almacenados en la BD
        recuperarMensajes();

    }


    private void recuperarMensajes(){
        mensajesRef = FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.MENSAJES);
        mensajesRef.orderByChild(FireBaseReferences.FECHA)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensaje mensaje = dataSnapshot.getValue(Mensaje.class);
                if(mensaje.getIdEmisor().equals(idEmisor)
                        && mensaje.getIdDestinatario().equals(idDestinatario)
                        || mensaje.getIdEmisor().equals(idDestinatario)
                        && mensaje.getIdDestinatario().equals(idEmisor)) {
                    adapter.addMensaje(mensaje);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Este metodo se ejecuta cuando se pulsa el boton enviar
     * y se encarga de guardar el mensaje en la BD
     */
    private void enviarMsj() {

        //Si las conversaciones no existen se crean.
        if(!emisortTieneConverascion)
            crearConversacion(idEmisor, idDestinatario, false);
        if(!destinatarioTieneConverascion)
            crearConversacion(idDestinatario, idEmisor, true);


        //Creo el objeto mensaje
        Mensaje mensaje = new Mensaje();
        mensaje.setCuerpoMensaje(mensajeET.getText().toString());
        mensaje.setIdEmisor(getIntent().getExtras()
                .getString(ExtrasRef.ID_EMISOR));
        mensaje.setIdDestinatario(getIntent().getExtras()
                .getString(ExtrasRef.ID_DESTINATARIO));

        //Guardo el mensaje en la BD
        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.MENSAJES);
        reference = reference.push();
        mensaje.setIdMensaje(reference.getKey());
        reference.setValue(mensaje);

        //Añado la fecha
        reference.child(FireBaseReferences.FECHA).setValue(ServerValue.TIMESTAMP);

        //Actualizo la conversacion del destinatario
        if(conversacionDestinatario!=null)
            actualizarTieneMensajes(conversacionDestinatario.getIdConversacion(), true);
        //Limpio el editText
        mensajeET.setText("");
    }

    /**
     * Comprueba que el destinatario tiene una
     * conversacion con este usuario si no la crea
     */
    private void comprobarConversacion(final String emisor, final String destinatario) {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.CONVERSACIONES);
        //Referencia apuntando al emisor
        tieneConversacionQuery = reference
                .orderByChild(FireBaseReferences.ID_EMISOR).equalTo(emisor);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean existeConversacion=false;
                for (DataSnapshot conversacion:dataSnapshot.getChildren()) {
                    Conversacion c = conversacion.getValue(Conversacion.class);
                    //Si en algun momento coinciden los idDestino la conversacion existe
                    if(c.getIdDestinatario().equals(destinatario)) {
                        existeConversacion = true;
                        if(idEmisor.equals(emisor)) {
                            emisortTieneConverascion = existeConversacion;
                            conversacionEmisor=c;
                            //actualizarTieneMensajes(c.getIdConversacion(), false);
                        }else if(idDestinatario.equals(emisor)) {
                            destinatarioTieneConverascion = existeConversacion;
                            conversacionDestinatario=c;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        tieneConversacionQuery.addValueEventListener(valueEventListener);
    }

    private void actualizarTieneMensajes(String idConversacion, boolean hayMensajes) {
        Log.w("Modifico", idConversacion+"     "+hayMensajes);
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().
                getReference(FireBaseReferences.CONVERSACIONES);
        reference.child(idConversacion)
                .child(FireBaseReferences.TIENE_MENSAJES)
                .setValue(hayMensajes);
    }

    /**
     * Este metodo se encarga de crear una conversacion en la BD
     * @param emisor
     * @param destinatario
     */
    private void crearConversacion(String emisor, String destinatario, boolean tieneMensajes) {
        Conversacion conversacion = new Conversacion();
        //Guardo la conversacion en la BD
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.CONVERSACIONES);
        reference = reference.push();
        conversacion.setIdConversacion(reference.getKey());
        conversacion.setIdEmisor(emisor);
        conversacion.setIdDestinatario(destinatario);
        conversacion.setTieneMensajes(tieneMensajes);
        reference.setValue(conversacion);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tieneConversacionQuery.removeEventListener(valueEventListener);
        if(conversacionEmisor!=null)
            actualizarTieneMensajes(conversacionEmisor.getIdConversacion(), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tieneConversacionQuery.removeEventListener(valueEventListener);
        if(conversacionEmisor!=null)
            actualizarTieneMensajes(conversacionEmisor.getIdConversacion(), false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tieneConversacionQuery.removeEventListener(valueEventListener);
        if(conversacionEmisor!=null)
            actualizarTieneMensajes(conversacionEmisor.getIdConversacion(), false);
    }
}


