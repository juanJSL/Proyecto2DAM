package com.example.jjsimon.proyectodam.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsimon.proyectodam.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Mapa extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        GoogleMap mMap;
        CameraUpdate camUpd;
        CameraPosition camPos;
        int vistaActual=0;

        mMap = map;



        // Add a marker in Sydney and move the camera
        LatLng initLocation = new LatLng(36.840835, -2.471172);
        mMap.addMarker(new MarkerOptions().position(initLocation).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initLocation));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

}
