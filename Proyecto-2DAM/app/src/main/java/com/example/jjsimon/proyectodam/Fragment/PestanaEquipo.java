package com.example.jjsimon.proyectodam.Fragment;

import android.content.Context;
import android.net.Uri;
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

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
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

    boolean tieneEquipo;

    private View fragmentEquipo=null;

    private ArrayList<Jugador> jugadorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private Equipo equipos;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Compruebo si el usuario tiene equipo par mostrar un layout u otro
        if(tieneEquipo()){
            cargarConEquipo(inflater, container, savedInstanceState);
        }else{
            cargarSinEquipo(inflater, container, savedInstanceState);
        }

        return fragmentEquipo;
    }


    /**
     * Este metodo se encarga de comprobar que el usuario pertenece a un equipo, en el caso
     * de que el usuario tenga un equipo carga la informacion de dicho equipo en la variable "equipo"
     * @return true -> si el usuario tiene equipo       false -> si el usuario no tiene equipo
     */
    private  boolean tieneEquipo(){
        //Cadena para almacenar el id del usuario actual
        String idUser = null;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Id del usuario actual
        idUser = user.getUid();

        //Referencia a la BD apuntando al nodo jugadores
        DatabaseReference bdd = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);

        //Busco el nodo con el id del usuario actual
        bdd.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Jugador jugador = dataSnapshot.getValue(Jugador.class);

                if(jugador.getEquipo()!=null)
                    tieneEquipo = true;
                else
                    tieneEquipo= false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("PERFIL", "Cancelled");
            }
        });
       return tieneEquipo;
    }//Fin tiene equipo


    /**
     * Este metodo carga el layout con la informacion del equipo al que pertenece el usuario
     */
    private void cargarConEquipo(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        fragmentEquipo = inflater.inflate(R.layout.fragment_equipo, container, false);

        //Inicializo la lista de jugadores
        inicializarLista();

        //Inicializo la RecyclerView
        recyclerView = (RecyclerView) fragmentEquipo.findViewById(R.id.equipoRecycler);

        //inicializo el adaptador le envio como parametro la lista de jugadores
        recyclerViewAdapter = new RecyclerViewAdapter(jugadorList);
        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(recyclerViewAdapter);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }//Fin cargar con equipo

    /**
     * Este metodo carga el layout en el caso de que el usuario aun no pertenezca a ningun equipo
     */
    private void cargarSinEquipo(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        fragmentEquipo = inflater.inflate(R.layout.fragment_pestana_sin_equipo, container, false);
    }

    /**
     * Este metodo realiza una consulta a la base de datos y rellena la lista de jugadores
     * con la que se cargaran las CardView
     */
    private void inicializarLista() {
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

}
