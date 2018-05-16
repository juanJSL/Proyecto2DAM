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
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
                Log.e("PERMISOS", "Permiso concedido");
            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.
                Toast.makeText(this, "Debes dar permisos para poder seleccionar la ubicacion", Toast.LENGTH_LONG).show();
                finish();
                Log.e("PERMISOS", "Permiso denegado");
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
            Log.e("UBIC_EQUIPO", "Tiene permisos");
            //Comprobar que el gps esta activo
            LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Log.w("UBIC_EQUIPO", "GPS activado");
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        double latitude = location.getLatitude();
                        double longitud = location.getLongitude();


                        Log.w("UBIC_EQUIPO", "Latitud " + latitude);
                        Log.w("UBIC_EQUIPO", "longitud " + longitud);

                        LatLng latLng = new LatLng(latitude, longitud);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicacion actual").draggable(true));

                        anyadirListener();


                    }
                });
            } else {
                //Si el gps no esta activado muestro un toast
                Toast.makeText(this, "Debes activar el GPS para recuperar tu ubicacion", Toast.LENGTH_LONG).show();
                Log.w("UBIC_EQUIPO", "GPS desactivado");
            }
        }else{
            Log.e("UBIC_EQUIPO", "No tiene permisos");
            pedirPermisos();
        }
    }


    public void pedirPermisos(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);
    }

    public void anyadirListener(){
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                marker.setPosition(latLng);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                crearDialog();
                return true;
            }
        });
    }

    public void crearDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.wubicequip_tilte_conf_dialog)
                .setMessage(R.string.wubicequip_msj_dialog)
                .setPositiveButton(R.string.si,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.w("DIALOGO", "OK");
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
                                Log.w("DIALOGO", "CANCELAR");
                            }
                        });

        builder.create().show();
    }//FIN CREAR DIALOG








}
