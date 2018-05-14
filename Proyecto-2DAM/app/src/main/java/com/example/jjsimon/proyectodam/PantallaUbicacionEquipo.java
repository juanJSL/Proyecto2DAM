package com.example.jjsimon.proyectodam;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PantallaUbicacionEquipo extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ubicacion_equipo);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap;
        CameraUpdate camUpd;
        CameraPosition camPos;
        int vistaActual = 0;

        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng initLocation = new LatLng(36.840835, -2.471172);
        mMap.addMarker(new MarkerOptions().position(initLocation).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initLocation));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        obtenerPosicionActual();


        Log.w("UBICACION", "onMapReady");
    }


    public void obtenerPosicionActual() {
        Log.w("UBICACION", "Metodo llamado");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();





        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitud = location.getLongitude();


        Log.w("UBICACION", "Latitud "+latitude);
        Log.w("UBICACION", "longitud "+longitud);
    }
}
