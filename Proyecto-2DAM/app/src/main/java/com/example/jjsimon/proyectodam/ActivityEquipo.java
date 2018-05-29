package com.example.jjsimon.proyectodam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapter;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityEquipo extends AppCompatActivity {
    private Equipo equipo;
    private String idEquipo;
    private Jugador jugador;

    private TextView nombreEquipoTV;

    private ArrayList<Jugador> jugadorList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        String nombreEquipo = getIntent().getExtras().getString(ExtrasRef.NOMBRE_EQUIPO);

        //Enlazo las view
        this.nombreEquipoTV = (TextView) findViewById(R.id.equipo_nombreTV);
        nombreEquipoTV.setText(getIntent().getExtras().getString(ExtrasRef.NOMBRE_EQUIPO));
        idEquipo = getIntent().getExtras().getString(ExtrasRef.ID_EQUIPO);


        jugadorList  = new ArrayList<>();


        //Inicializo la RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.equipo_Recycler);

        //inicializo el adaptador le envio como parametro la lista de jugadores
        recyclerViewAdapter = new RecyclerViewAdapter(jugadorList);

        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(recyclerViewAdapter);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        consultarJugadores(idEquipo);
    }

    /**
     * Este metodo realiza una consulta a la base de datos y rellena la lista de jugadores
     * con la que se cargaran las CardView
     */
    private void inicializarLista() {

        consultarJugadores(getIntent().getExtras().getString(ExtrasRef.ID_EQUIPO));
        //Jugadores de prueba despues se debe sustituir por una consulta a la BD
        /*Jugador j1 = new Jugador("Juan Javier", "Jota", "Fusilero");
        Jugador j2 = new Jugador("Antonio", "Liria", "Fuslier");
        Jugador j3 = new Jugador("Jose Luis", "Kiles", "Sniper");
        Jugador j4 = new Jugador("Gabi", "Gabi", "Sniper/Fusilero/Apollo");
        Jugador j5 = new Jugador("Jose Ramon", "Rizos", "Fusilero/Apollo");

        jugadorList.add(j1);
        jugadorList.add(j2);
        jugadorList.add(j3);
        jugadorList.add(j4);
        jugadorList.add(j5);*/
    }//Fin inicializar lista


    private void consultarJugadores(final String idEquipo){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(FireBaseReferences.JUGADORES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugadorList.removeAll(jugadorList);
                Log.w("NUEVOUSU", idEquipo);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Jugador jugador = snapshot.getValue(Jugador.class);
                    if(jugador.getIdEquipo().equals(idEquipo)){
                            jugadorList.add(jugador);
                    }
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
