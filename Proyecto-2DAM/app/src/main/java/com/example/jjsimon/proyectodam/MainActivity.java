package com.example.jjsimon.proyectodam;



import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.jjsimon.proyectodam.Adaptadores.ViewPagerAdapter;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.EscuchaDeMensajes.ServicioNotificaciones;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.Fragment.Chat;
import com.example.jjsimon.proyectodam.Fragment.PestanaEquipo;
import com.example.jjsimon.proyectodam.Fragment.Mapa;
import com.example.jjsimon.proyectodam.Fragment.Perfil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private  ViewPagerAdapter adapter;

    private Intent servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializo el ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //Inicializo el adaptador
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Creo los fragment
        Mapa mapa = new Mapa();
        Perfil perfil = new Perfil();
        PestanaEquipo pestanaEquipo = new PestanaEquipo();
        Chat chat = new Chat();

        //Añado los fragment al adaptador
        adapter.addFragment(mapa);
        adapter.addFragment(perfil);
        adapter.addFragment(pestanaEquipo);
        adapter.addFragment(chat);

        //Enlazo el adaptador con el view pager
        viewPager.setAdapter(adapter);

        //Enlazo el viewPager con el tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayouts);
        tabLayout.setupWithViewPager(viewPager);

        //Cambio los titulos de las pestañas
        tabLayout.getTabAt(0).setText("Mapa");
        tabLayout.getTabAt(1).setText("Perfil");
        tabLayout.getTabAt(2).setText("Equipo");
        tabLayout.getTabAt(3).setText("Chat");

        //Inicio el servicio que estara a la escucha de mensajes
        servicio = new Intent(this, ServicioNotificaciones.class);
        startService(servicio);
    }

    /**
     * Este metodo se encarga de inflar el menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.cerrar_sesion) {
            finish();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, PantallaLogin.class));
        }else if(item.getItemId() == R.id.crear_equipo){
            abrirCreaarEquipo();
        }

        return super.onOptionsItemSelected(item);
    }

    private void abrirCreaarEquipo() {
        //Compruebo si el usuario ya tiene un equipo
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Jugador jugador = dataSnapshot.getValue(Jugador.class);
                if(jugador.getIdEquipo()!=null){
                    //Si el jugador ya tiene un equipo muestro un toast indicandole que no puede crear otro
                    Toast.makeText(getBaseContext(), R.string.ya_tienes_equipo, Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(getBaseContext(), CrearEquipo.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("SERVICIO", "main onDestroy");
        stopService(servicio);
    }


    /**
     * Se supone que con esto puedo actualizar el fragment de la pestaña equipo
     */
    public void actualizar(){
        viewPager.getAdapter().notifyDataSetChanged();
    }
}
