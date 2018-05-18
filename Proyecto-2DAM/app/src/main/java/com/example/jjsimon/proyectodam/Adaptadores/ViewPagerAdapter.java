package com.example.jjsimon.proyectodam.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.jjsimon.proyectodam.Fragment.Chat;
import com.example.jjsimon.proyectodam.Fragment.Mapa;
import com.example.jjsimon.proyectodam.Fragment.Perfil;
import com.example.jjsimon.proyectodam.Fragment.PestanaEquipo;
import com.example.jjsimon.proyectodam.Fragment.PestanaSinEquipo;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Metodo que devuelce el fragment de la posicion que recibe como parametro
    @Override
    public Fragment getItem(int position) {
        Log.w("VIEW_PAGER_ADAPTER", "getItem llamado");
        switch (position) {
            case 0:
                Mapa mapa = new Mapa();
                addFragment(mapa);
                return mapa;

            case 1:
                Perfil perfil = new Perfil();
                addFragment(perfil);
                return perfil;

            case 2:
                //return new PestanaEquipo();
                PestanaSinEquipo pestanaSinEquipo = new PestanaSinEquipo();
                addFragment(pestanaSinEquipo);
                return pestanaSinEquipo;

            case 3:
                Chat chat = new Chat();
                addFragment(chat);
                return chat;

        }
        return fragmentList.get(position);
    }

    //Metodo que devuelve el tamaño de la lista de fragment
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //Metodo que permite añadir un fragment a la lista
    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }

    //Metodo para añadir un fragment en una posicion reemplazando el que existe
    public void addFragmentAtPosition(Fragment fragment, int position){
        fragmentList.remove(position);
        fragmentList.add(position, fragment);
    }

    //Metodo que permite limpiar la lista
    public void cleanLista(){
        for (int i=0; i<fragmentList.size(); i++){
            fragmentList.remove(i);
        }
    }


}
