package com.example.jjsimon.proyectodam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jjsimon.proyectodam.PantallaCrearCuenta;
import com.example.jjsimon.proyectodam.PantallaLogin;
import com.example.jjsimon.proyectodam.R;

public class Chat extends Fragment {
    Button abrirLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View fragment = inflater.inflate(R.layout.fragment_chat, container, false);
        Log.w("Boton 1", "Boton pulsado");
        abrirLogin = fragment.findViewById(R.id.abrirCrearCuenta);

        abrirLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(new Intent(getContext(), PantallaLogin.class));
                //startActivity(new Intent(getContext(), PantallaLogin.class));
                Log.w("Boton 1", "Boton pulsado");
            }
        });


        return fragment;



        //return fragment;
    }
/*
    public void abrirLogin(){
        startActivity(new Intent(getContext(), PantallaLogin.class));
        Log.w("Boton 1", "Boton pulsado");
    }
*/
    public void abrirCrearCuenta(View v){
        startActivity(new Intent(getContext(), PantallaCrearCuenta.class));
    }
}
