package com.example.jjsimon.proyectodam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.CrearEquipo;
import com.example.jjsimon.proyectodam.R;

public class PestanaSinEquipo extends Fragment {
    private Button crearEquipoBT;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pestana_sin_equipo, container, false);

        //Enlazo las view
        crearEquipoBT = (Button) view.findViewById(R.id.crearEquipoBT);
        //Añado el evento para el boton
        crearEquipoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), CrearEquipo.class));
            }
        });

        return view;
    }
}
