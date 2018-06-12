package com.example.jjsimon.proyectodam.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
