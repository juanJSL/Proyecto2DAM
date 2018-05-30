package com.example.jjsimon.proyectodam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;

public class JugadorActivity extends AppCompatActivity {

    private TextView nickTV;
    private TextView rolTV;
    private TextView nombreEquipoTV;
    private String idJugador;
    private Button enviarPrivadoBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        //Enlazo las view
        enviarPrivadoBT = findViewById(R.id.enviar_privado_bt);
        nickTV = findViewById(R.id.nick_tv);
        rolTV = findViewById(R.id.rol_tv);
        nombreEquipoTV = findViewById(R.id.equipo_tv);

        nickTV.setText(getIntent().getExtras().getString(ExtrasRef.NICK_JUGADOR));
        rolTV.setText(getIntent().getExtras().getString(ExtrasRef.ROL_JUGADOR));
        idJugador = getIntent().getExtras().getString(ExtrasRef.ID_JUGADOR);


        //consultar equipo


        enviarPrivadoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JugadorActivity.this, MDActivity.class));
            }
        });
    }
}
