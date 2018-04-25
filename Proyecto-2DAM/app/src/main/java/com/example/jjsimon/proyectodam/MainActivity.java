package com.example.jjsimon.proyectodam;



import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;


import com.example.jjsimon.proyectodam.Adaptadores.ViewPagerAdapter;
import com.example.jjsimon.proyectodam.Fragment.Chat;
import com.example.jjsimon.proyectodam.Fragment.Equipo;
import com.example.jjsimon.proyectodam.Fragment.Mapa;
import com.example.jjsimon.proyectodam.Fragment.Perfil;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }


    /**
     * Este metodo carga los fragment
     */
    public void cargarViewPager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Creo los fragment
        Mapa mapa = new Mapa();
        Perfil perfil = new Perfil();
        Equipo equipo = new Equipo();
        Chat chat = new Chat();

        //adapter.addFragment(mapa);
        //adapter.addFragment(perfil);
        //adapter.addFragment(equipo);
        //adapter.addFragment(chat);

        Fragment[] array = {mapa, perfil, equipo, chat};

        //Añado los fragment
        for (Fragment f:array){
            adapter.addFragment(f);
        }

        viewPager.setAdapter(adapter);

    }
}
