package com.example.jjsimon.proyectodam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CrearEquipo extends AppCompatActivity {

    private Button abrirMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_equipo);

        abrirMapa = (Button) findViewById(R.id.wcequip_ubicacion_bt);

        abrirMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearEquipo.this, PantallaUbicacionEquipo.class));
                //startActivity(new Intent(CrearEquipo.this, PruebaObtenerUbicacion.class));
            }
        });
    }
}
