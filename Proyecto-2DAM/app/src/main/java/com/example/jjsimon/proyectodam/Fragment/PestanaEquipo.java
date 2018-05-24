package com.example.jjsimon.proyectodam.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.CrearEquipo;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PestanaEquipo extends Fragment {

    private Equipo equipo;
    private String idEquipo;
    private Jugador jugador;

    private TextView nombreEquipoET;

    private ArrayList<Jugador> jugadorList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentEquipo = inflater.inflate(R.layout.fragment_equipo, container, false);

        //Enlazo las view
        nombreEquipoET = (TextView) fragmentEquipo.findViewById(R.id.wequipo_nombreTV);

        //Inicializo la lista de jugadores
        if(jugadorList==null)
            inicializarLista();

        //Inicializo la RecyclerView
        recyclerView = (RecyclerView) fragmentEquipo.findViewById(R.id.we_equipoRecycler);

        //inicializo el adaptador le envio como parametro la lista de jugadores
        recyclerViewAdapter = new RecyclerViewAdapter(jugadorList);

        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(recyclerViewAdapter);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        consultarJugador();
        return fragmentEquipo;
    }




    /**
     * Este metodo realiza una consulta a la base de datos y rellena la lista de jugadores
     * con la que se cargaran las CardView
     */
    private void inicializarLista() {
        jugadorList  = new ArrayList<>();
        //Jugadores de prueba despues se debe sustituir por una consulta a la BD
        Jugador j1 = new Jugador("Juan Javier", "Jota", "Fusilero");
        Jugador j2 = new Jugador("Antonio", "Liria", "Fuslier");
        Jugador j3 = new Jugador("Jose Luis", "Kiles", "Sniper");
        Jugador j4 = new Jugador("Gabi", "Gabi", "Sniper/Fusilero/Apollo");
        Jugador j5 = new Jugador("Jose Ramon", "Rizos", "Fusilero/Apollo");

        jugadorList.add(j1);
        jugadorList.add(j2);
        jugadorList.add(j3);
        jugadorList.add(j4);
        jugadorList.add(j5);
    }//Fin inicializar lista




    /**
     * Este metodo hace una consulta a la base de datos y obtiene los datos del equipo al que pertenece el usuario
     */
    public void cargarDatosEquipo(){
        //Referencia a la BD apuntando al nodo EQUIPOS
        DatabaseReference bdd = FirebaseDatabase.getInstance().getReference(FireBaseReferences.EQUIPOS);

        //Busco el nodo con el id del equipo del usuario
        bdd.child(idEquipo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                equipo = dataSnapshot.getValue(Equipo.class);
                nombreEquipoET.setText(equipo.getNombre());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }// FIN CARGAR DATOS EQUIPO


    /**
     * Consulto los datos del usuario actual para obtener el id de su equipo
     */
    public void consultarJugador(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);

        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugador = dataSnapshot.getValue(Jugador.class);
                idEquipo = jugador.getIdEquipo();
                cargarDatosEquipo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
