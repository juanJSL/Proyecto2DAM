package com.example.jjsimon.proyectodam;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class PantallaUbicacionEquipo extends FragmentActivity implements OnMapReadyCallback{

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    private Marker marker;

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
        mMap = googleMap;

        LatLng initLocation = new LatLng(36.840835, -2.471172);

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initLocation,1));
        obtenerUbicacion();

        //Añado la funcionalidad en el caso de pulsar de panera prolongada en un punto
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                marker.setPosition(latLng);
            }
        });

        //Añado la funcionalidad para cuando pulsamos encima de un marcador
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                crearDialog();
                return true;
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            } else {
                Toast.makeText(this, "Debes dar permisos para poder seleccionar la ubicacion", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


    /**
     * Este metodo obtiene la ubicacion actual del usuario y la guarda en una variable
     */
    private void obtenerUbicacion(){

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        //Compruebo los permisos
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Comprobar que el gps esta activo
            LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        double latitude = location.getLatitude();
                        double longitud = location.getLongitude();

                        LatLng latLng = new LatLng(latitude, longitud);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicacion actual").draggable(true));
                    }
                });
            } else {
                //Si el gps no esta activado muestro un toast
                Toast.makeText(this, "Debes activar el GPS para recuperar tu ubicacion", Toast.LENGTH_LONG).show();
            }
        }else{
            //Si no tiene permisos los pido
            Utilidades.pedirPermisosUbicacion(this, PETICION_PERMISO_LOCALIZACION);
        }
    }



    public void crearDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.wubicequip_tilte_conf_dialog)
                .setMessage(R.string.wubicequip_msj_dialog)
                .setPositiveButton(R.string.si,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent();
                                String ubicacion = marker.getPosition().latitude+","+marker.getPosition().longitude;
                                i.putExtra("ubicacion",ubicacion);
                                setResult(RESULT_OK, i);
                                finish();
                            }
                        })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.w( "DIALOGO", "CANCELAR");
                            }
                        });

        builder.create().show();
    }//FIN CREAR DIALOG








}
