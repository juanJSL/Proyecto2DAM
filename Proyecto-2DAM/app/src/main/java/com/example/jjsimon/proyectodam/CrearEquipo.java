package com.example.jjsimon.proyectodam;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.Fragment.PestanaEquipo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    /**
     * Este metodo se encarga de guardar en la base de datos un nuevo equipo, para ello crea una
     * referencia a la base de datos apuntando al nodo EQUIPOS, con esta referencia a la BD
     * llamos al metodo push que devuelve una referencia a la base de datos, esta referencia será
     * el nuevo nodo dentro de "EQUIPOS", de está referencia obtengo la key (llave primaria)
     * y la guardo en una variable auxiliar de tipo cadena, una vez tengo la llave primaria
     * para el nuevo equipo lo guardo en la base de datos y modifico la informacion del usuario
     * para indicar que pertenece a ese equipo
     */
    private void guardarEquipo() {
        String idEquipo;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Creo el objeto equipo con los datos que se deben guardar
        equipo = new Equipo("idequipo", nombreEquipo.getText()+"", ubicacion, descripcionEquipo.getText()+"", "escudo", user.getUid() );

        //Creo una referencia a la base de datos
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(FireBaseReferences.EQUIPOS);
        //Obtengo la referencia par ael nuevo nodo
        databaseReference = databaseReference.push();
        //Obtengo la key para la nueva entrada
        idEquipo = databaseReference.getKey();
        equipo.setIdEquipo(idEquipo);
        //Guardo el objeto equipo en la BD
        databaseReference.setValue(equipo);


        //Llamo al metodo encargado de actualizarUsuario para que actualice el equipo del usuario actual
        actualizarUsuario(idEquipo, user.getUid());
    }


    /**
     * Este metodo se encarga de modificar la informacion del usuario añadiendo el id del equipo al que pertenece
     * @param idEquipo  cadena que contiene el id del equipo
     * @param idJugador cadena con el id del usuario que se debe modificar
     */
    private void actualizarUsuario(String idEquipo, String idJugador) {
        //Referencia a la base de datos apuntando al nodo jugadores
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);

        databaseReference.child(idJugador).child(FireBaseReferences.ID_EQUIPO).setValue(idEquipo);


        finish();
    }





}

