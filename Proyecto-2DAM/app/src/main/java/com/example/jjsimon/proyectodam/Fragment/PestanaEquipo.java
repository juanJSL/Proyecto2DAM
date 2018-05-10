package com.example.jjsimon.proyectodam.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapter;

import java.util.ArrayList;

public class PestanaEquipo extends Fragment {


    private ArrayList<Jugador> jugadorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentEquipo = inflater.inflate(R.layout.fragment_equipo, container, false);

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


        return fragmentEquipo;
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
    }
}
