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

import com.example.jjsimon.proyectodam.Clases.ALD;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapter;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterALD;

import java.util.ArrayList;

public class Perfil extends Fragment {
    private ArrayList<ALD> aldList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapterALD adapterALD;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_perfil, container, false);

        //Inicializo la lista de ALD
        inicializarLista();

        //Inicializo la RecyclerView
        recyclerView = (RecyclerView) fragment.findViewById(R.id.perfilRecycler);

        //inicializo el adaptador le envio como parametro la lista de jugadores
        adapterALD = new RecyclerViewAdapterALD(aldList);
        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(adapterALD);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return fragment;
    }






    /**
     * Este metodo realiza una consulta a la base de datos y rellena la lista de ALD
     * con la que se cargaran las CardView
     */
    private void inicializarLista() {
        //ALD de prueba despues se debe sustituir por una consulta a la BD
        ALD a1 = new ALD(1, "G36c", "Fusilero", 350);
        ALD a2 = new ALD(2, "HK416", "Fusilero", 350);
        ALD a3 = new ALD(3, "L96", "Sniper", 500);
        ALD a4 = new ALD(4, "P90", "Fusilero", 350);
        ALD a5 = new ALD(5, "SPR", "DMR", 450);

        aldList.add(a1);
        aldList.add(a2);
        aldList.add(a3);
        aldList.add(a4);
        aldList.add(a5);
    }
}
