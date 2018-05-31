package com.example.jjsimon.proyectodam.Fragment;


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
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterJugador;
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

    private ArrayList<Jugador> jugadoresList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterJugador recyclerViewAdapterJugador;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentEquipo = inflater.inflate(R.layout.fragment_equipo, container, false);

        //Enlazo las view
        nombreEquipoET = (TextView) fragmentEquipo.findViewById(R.id.wequipo_nombreTV);
        recyclerView = (RecyclerView) fragmentEquipo.findViewById(R.id.we_equipoRecycler);

        //Instancio la lista
        jugadoresList = new ArrayList<>();

        //inicializo el adaptador le envio como parametro la lista de jugadores
        recyclerViewAdapterJugador = new RecyclerViewAdapterJugador(jugadoresList);

        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(recyclerViewAdapterJugador);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //Realizo las consultas necesarias a la BD para completar la informacion
        consultarJugador();
        inicializarLista();
        return fragmentEquipo;
    }



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
    }//FIN CONSULTAR JUGADOR



    /**
     * Este metodo hace una consulta a la base de datos y obtiene los datos del equipo al que pertenece el usuario
     */
    public void cargarDatosEquipo(){
        //Referencia a la BD apuntando al nodo EQUIPOS
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.EQUIPOS);

        //Busco el nodo con el id del equipo del usuario
        reference.child(idEquipo).addListenerForSingleValueEvent(new ValueEventListener() {
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
     * Este metodo realiza una consulta a la base de datos y rellena la lista de jugadores
     * con la que se cargaran las CardView
     */
    private void inicializarLista() {
        //Referencia a la BD en el nodo JUGADORES
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);
        //Busco dentro de los nodos jugadores los que tengan el id del equipo actual
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Vacio la lista de jugadores
                jugadoresList.removeAll(jugadoresList);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Log.w("ERROOOOORR", snapshot+"");
                    Jugador j = snapshot.getValue(Jugador.class);
                    Log.w("ERROOOOORR", j+"");
                    if(j.getIdEquipo()!=null && j.getIdEquipo().equals(idEquipo))
                        jugadoresList.add(j);
                }
                recyclerViewAdapterJugador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//FIN INICIALIZAR LISTA

}
