package com.example.jjsimon.proyectodam;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class PruebaObtenerUbicacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_obtener_ubicacion);

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Comprobar que el gps esta activo
            LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.w("PERMISOS", "Tiene permisos");
                        double latitude = location.getLatitude();
                        double longitud = location.getLongitude();


                        Log.w("LOCALIZACION", "Latitud " + latitude);
                        Log.w("LOCALIZACION", "longitud " + longitud);
                    }
                });
            } else {
                //Comprobar permisos
                Log.w("PERMISOS", "No tiene permisos");
            }
        }
    }
}
