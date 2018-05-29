package com.example.jjsimon.proyectodam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jjsimon.proyectodam.Clases.Conversacion;
import com.example.jjsimon.proyectodam.PantallaCrearCuenta;
import com.example.jjsimon.proyectodam.PantallaLogin;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterConversaciones;

import java.util.ArrayList;

public class Chat extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Conversacion> conversacionesList;
    private RecyclerViewAdapterConversaciones adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View fragment = inflater.inflate(R.layout.fragment_chat, container, false);

        //Enlazo la RecyclerView
        recyclerView =(RecyclerView) fragment.findViewById(R.id.recycler_conversaciones);

        conversacionesList = new ArrayList<>();

        iniciarLista();

        adapter = new RecyclerViewAdapterConversaciones(conversacionesList);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return fragment;
    }


    /**
     * METODO PARA INTRODUCIR DATOS DE PRUEBA
     */
    private void iniciarLista(){
        conversacionesList.removeAll(conversacionesList);

        Conversacion c = new Conversacion("idEmisor", "idReceptor", "EMISOR 0");
        Conversacion c1 = new Conversacion("idEmisor", "idReceptor", "EMISOR 1");
        Conversacion c2 = new Conversacion("idEmisor", "idReceptor", "EMISOR 2");
        Conversacion c3 = new Conversacion("idEmisor", "idReceptor", "EMISOR 3");
        Conversacion c4 = new Conversacion("idEmisor", "idReceptor", "EMISOR 4");
        Conversacion c5 = new Conversacion("idEmisor", "idReceptor", "EMISOR 6");

        Conversacion [] array = {c ,c1, c2, c3, c4, c5};

        for (Conversacion conversacion: array) {
            conversacionesList.add(conversacion);
        }
    }


}
