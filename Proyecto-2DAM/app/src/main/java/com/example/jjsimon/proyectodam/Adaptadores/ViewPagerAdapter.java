package com.example.jjsimon.proyectodam.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase que extiende FragmentPagerAdapter y es la encargada de guardar un
 * Fragment cuando no se está utilizando deontro de un conjunto de pestañas
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Metodo que devuelve el fragment de la posicion que recibe como parametro
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

    public void clearAdapter(){
        fragmentList.removeAll(fragmentList);
    }

}
