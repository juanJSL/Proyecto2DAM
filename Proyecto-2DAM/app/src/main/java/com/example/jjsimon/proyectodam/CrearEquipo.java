package com.example.jjsimon.proyectodam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearEquipo extends AppCompatActivity {
    private int PANTALLA_UBICACION_EQUIPO = 1;

    //View de la pantalla
    private Button abrirMapa;
    private Button aceptarBT;
    private EditText nombreEquipo;
    private EditText descripcionEquipo;

    //Variable para guardar la ubicacion
    private String ubicacion;

    //Variable para guardar los datos que se almacenaran en la BD;
    private Equipo equipo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_equipo);

        //Enlazo las view
        abrirMapa = (Button) findViewById(R.id.wcequip_ubicacion_bt);
        aceptarBT = (Button) findViewById(R.id.wcequip_aceptar_bt);
        nombreEquipo = (EditText) findViewById(R.id.wcequipo_nombre_et);
        descripcionEquipo = (EditText) findViewById(R.id.wcequip_descrip_et);

        anyadirListener();

    }


    /**
     * En este metodo se añaden los listener para registrar los eventos al pulsar los botones
     */
    private void anyadirListener(){

        //Registro el evento para el boton  ¿Donde se encuentra tu equipo?
        //Cuando se pulsa este boton se abre una nueva actividad para elegir la ubicacion del equipo
        abrirMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrearEquipo.this, PantallaUbicacionEquipo.class);
                startActivityForResult(i, PANTALLA_UBICACION_EQUIPO);
            }
        });


        //Registro el evento para el boton "Aceptar"
        //Cuando se pulsa el boton Aceptar se guarda el equipo en la BD
        aceptarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEquipo();
            }
        });
    }

    /**
     * Este metodo se ejecuta cuando se vuelve de la actividad PantallaUbicacionEequipo
     * cuando es llamado se guarda la ubicacion en una variable local
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PANTALLA_UBICACION_EQUIPO && resultCode == RESULT_OK) {
            ubicacion = data.getExtras().getString("ubicacion");
        }
    }

    private void guardarEquipo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        equipo = new Equipo("idequipo", nombreEquipo.getText()+"", ubicacion, descripcionEquipo.getText()+"", "escudo", auth.getCurrentUser().getUid() );

        //Creo una referencia a la base de datos
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.w("creando_equipo", databaseReference.child(FireBaseReferences.EQUIPOS).push().getKey());
    }

}