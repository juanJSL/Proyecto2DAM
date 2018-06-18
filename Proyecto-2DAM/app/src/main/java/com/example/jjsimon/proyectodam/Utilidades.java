package com.example.jjsimon.proyectodam;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class Utilidades {
    private static LatLng latLng;

    /**
     *  Abre un dialogo que pide los permisos para la localización
     * @param activity --> Actividad desde donde es llamado
     * @param codigo --> Código para gestionar la respuesta
     */
    public static void pedirPermisosUbicacion(Activity activity, int codigo){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},codigo);
    }

}
