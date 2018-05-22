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





    /**
     * Este metodo obtiene la ubicacion actual del usuario y la guarda en una variable
     */
    public static LatLng obtenerUbicacion(Context context, int cod, Activity activity){
        latLng = null;
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);

        //Compruebo los permisos
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Comprobar que el gps esta activo
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        double latitude = location.getLatitude();
                        double longitud = location.getLongitude();

                        latLng = new LatLng(latitude, longitud);
                        Log.w("UTILIDADES", latLng+"");
                    }
                });
            } else {
                //Si el gps no esta activado muestro un toast
                Toast.makeText(context, "Debes activar el GPS para recuperar tu ubicacion", Toast.LENGTH_LONG).show();
            }
        }else{
            //Si no tiene permisos los pido
            Utilidades.pedirPermisosUbicacion(activity, cod);
        }

        return latLng;
    }

}
