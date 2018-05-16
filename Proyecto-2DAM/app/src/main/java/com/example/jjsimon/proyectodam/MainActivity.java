package com.example.jjsimon.proyectodam;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.util.Log;


import com.example.jjsimon.proyectodam.Adaptadores.ViewPagerAdapter;
import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Equipo2;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.Fragment.Chat;
import com.example.jjsimon.proyectodam.Fragment.PestanaEquipo;
import com.example.jjsimon.proyectodam.Fragment.Mapa;
import com.example.jjsimon.proyectodam.Fragment.Perfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Abro la pantalla de crear cuenta
        //startActivity(new Intent(this, PantallaCrearCuenta.class));







        //Inicializo el ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //Llamo al metodo que se encarga de cargar el ViewPager
        cargarViewPager();
        //Enlazo el viewPager con el tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayouts);
        tabLayout.setupWithViewPager(viewPager);
        //Cambio los titulos de las pestañas
        tabLayout.getTabAt(0).setText("Mapa");
        tabLayout.getTabAt(1).setText("Perfil");
        tabLayout.getTabAt(2).setText("Equipo");
        tabLayout.getTabAt(3).setText("Chat");



        //pruebaFireBase();
        //pruebaGuardarFirebase();
        //pruebaPreferencias();
        //Log.w("CONEXION", ""+FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //pruebaDatosUserNow();
    }


    /**
     * Este metodo carga los fragment
     */
    public void cargarViewPager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Creo los fragment
        Mapa mapa = new Mapa();
        Perfil perfil = new Perfil();
        PestanaEquipo pestanaEquipo = new PestanaEquipo();
        Chat chat = new Chat();

        //adapter.addFragment(mapa);
        //adapter.addFragment(perfil);
        //adapter.addFragment(equipo);
        //adapter.addFragment(chat);

        Fragment[] array = {mapa, perfil, pestanaEquipo, chat};

        //Añado los fragment
        for (Fragment f:array){
            adapter.addFragment(f);
        }

        viewPager.setAdapter(adapter);

    }


    /**
     * Ejemplo de como leer de la base de datos
     */
    public void pruebaFireBase (){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tutorialRef = database.getReference("equipos");
        tutorialRef.child("prueba").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Equipo equipo = dataSnapshot.getValue(Equipo.class);
                Log.w("EQUIPO:       ", equipo.getNombre()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Ejemplo de como guardar datos en firebase
     */
    public void pruebaGuardarFirebase(){
        Log.w("FIREBASE", "Entra");
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        /*int id = 2;
        String nombre = "SEAL";
        String ubicacion = "Cuevas";
        String desc = "Descripcion";
        String escudo = "Escudo";*/


        //Equipo e = new Equipo(2, "SEAL", "Cuevas", "Equipo de cuevas", "Escudo SEAL");

        //databaseReference.child("equipos").child(e.getId_equipo()+"").setValue(e);
        //mDatabase.child("users").child(userId).setValue(user);
    }

    /**
     * BORRAR
     */
    public void pruebaPreferencias(){
        SharedPreferences preferences = getSharedPreferences(PreferenciasReference.PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("logueado", true);
        editor.commit();
        Log.w("PREFERENCIAS", ""+preferences.getBoolean(PreferenciasReference.LOGUEADO, false));
    }


    public void pruebaDatosUserNow(){
        //Cadena para almacenar el id del usuario actual
        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Objeto Query para hacer la consulta a la BD
        Query query;

        //Referencia a la BD apuntando al nodo jugadores
        DatabaseReference bdd = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);

        //Busco el nodo con el id del usuario actual
        bdd.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Jugador j = dataSnapshot.getValue(Jugador.class);
                Log.w("CONSULTA", "NICK "+j.getNick());
                Log.w("CONSULTA", "ID "+j.getIdJugador());
                Log.w("CONSULTA", "MAIL "+j.getMail());
                //Log.w("CONSULTA", "NICK "+j.getNick());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


/*
        //Ordeno por idJugador
        query = bdd.orderByChild(FireBaseReferences.ID_JUGADOR).equalTo(idUser);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Jugador j = dataSnapshot.getValue(Jugador.class);
                //Log.w("CONSULTA", j.getNick()+"");
                int contador = 0;
                for(DataSnapshot dataSnaps : dataSnapshot.getChildren()){
                    contador++;
                    Log.w("CONSULTA", "Clase"+ dataSnapshot.getChildren().getClass());
                }
                Log.w("CONSULTA", "Contador "+contador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
    }

}
