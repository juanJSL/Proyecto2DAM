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
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterJugador;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityEquipo extends AppCompatActivity {
    private Equipo equipo;
    private String idEquipo;
    private Jugador jugador;

    private TextView nombreEquipoTV;

    private ArrayList<Jugador> jugadorList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterJugador recyclerViewAdapterJugador;

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
        recyclerViewAdapterJugador = new RecyclerViewAdapterJugador(jugadorList);

        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(recyclerViewAdapterJugador);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        consultarJugadores();
    }


    private void consultarJugadores(){
        //Referencia a la BD en el nodo JUGADORES
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);

        Query query = reference.orderByChild(FireBaseReferences.ID_EQUIPO).equalTo(idEquipo);
        //Busco dentro de los nodos jugadores los que tengan el id del equipo actual
        query.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugadorList.removeAll(jugadorList);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Jugador jugador = snapshot.getValue(Jugador.class);
                    jugadorList.add(jugador);
                }
                recyclerViewAdapterJugador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//FIN CONSULTAR JUGADORES

}
