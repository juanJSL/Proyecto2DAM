package com.example.jjsimon.proyectodam;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class PantallaUbicacionEquipo extends FragmentActivity implements OnMapReadyCallback{

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private Location loc;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ubicacion_equipo);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pedirPermisos();


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




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                obtenerUbicacion();
            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e("LOCALIZACIONS", "Permiso denegado");
            }
        }
    }




    private Location obtenerUbicacion(){
        loc = null;

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Comprobar que el gps esta activo
            LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.w("PERMISOS", "Tiene permisos");
                        double latitude = location.getLatitude();
                        double longitud = location.getLongitude();


                        Log.w("LOCALIZACION", "Latitud "+latitude);
                        Log.w("LOCALIZACION", "longitud "+longitud);

                        loc = location;
                    }
                });
            } else {
                //Comprobar permisos
                Log.w("PERMISOS", "No tiene permisos");
            }
        }

        return loc;
    }


    public void pedirPermisos(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);
    }
}
