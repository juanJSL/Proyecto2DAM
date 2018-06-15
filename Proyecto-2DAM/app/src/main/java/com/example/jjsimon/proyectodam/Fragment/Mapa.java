package com.example.jjsimon.proyectodam.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjsimon.proyectodam.ActivityEquipo;
import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.example.jjsimon.proyectodam.Utilidades;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;


public class Mapa extends SupportMapFragment implements OnMapReadyCallback {
    private static final int PETICION_PERMISO_LOCALIZACION = 120;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);
        return rootView;
    }

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        LatLng initLocation = new LatLng(36.840835, -2.471172);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initLocation));

        anyadirListener();//Añado los listener al mapa
        consultarEquipos();//Hago la consulta a la BD y pongo los marcadores para los euqipos
        ubicacionActual();//Cambio la posicion del mapa a la ubicacion actual
    }


    /**
     * Este metodo hace una consulta a la base de datos y recupera los equipos almacenados en ella
     * cada vez que recupera un equipo pone un marcador en el mapa
     */
    private void consultarEquipos(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FireBaseReferences.EQUIPOS).addListenerForSingleValueEvent(new ValueEventListener() {
            //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Recorro el resultado de la consulta
                for (DataSnapshot equiposSnapshot : dataSnapshot.getChildren()){
                    //Guardo los datos en un objeto del tipo equipo
                    Equipo equipo = equiposSnapshot.getValue(Equipo.class);
                    Log.w("EQUIPOSS", equipo.toString());
                    ponerMarcador(equipo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//FIN CONSULTAR EQUIPOS

    /**
     * Este metido se encarga de poner un marcador en el mapa con los datos del equipo
     * @param equipo Objeto del tipo equipo con los datos obtenidos de la BD
     */
    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ponerMarcador(Equipo equipo){

        //Obtengo la ubicacion del objeto equipo
        String [] ubicacion = equipo.getUbicacion().split(",");
        LatLng latLng = new LatLng(Double.parseDouble(ubicacion[0]),Double.parseDouble(ubicacion[1]));

        //Cambio el tipo de marcador
        TextView text = new TextView(getContext());
        text.setText(equipo.getNombre());
        IconGenerator generator = new IconGenerator(getContext());

        generator.setBackground(getResources().getDrawable(R.drawable.amu_bubble_mask));
        generator.setContentView(text);
        generator.setColor(Color.WHITE);
        Bitmap icon = generator.makeIcon();

        //Creo y añado el marcador
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(equipo.getNombre())
                .snippet(equipo.getDescripcion())
                .icon(BitmapDescriptorFactory.fromBitmap(icon));
        /*Añado un tag al marcador de manera que guarde el objeto equipo, ya que los datos que almacena
        * este objeto seran necesarios para abrir una actividad cuando se pulse */
        mMap.addMarker(markerOptions).setTag(equipo);

    }//FIN PONER MARCADOR


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ubicacionActual();
            } else {
                Toast.makeText(getContext(), "Debes dar permisos para poder seleccionar la ubicacion", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Este metodo obtiene la ubicacion actual del usuario y mueve la camara del mapa hasta esa posicion
     */
    private void ubicacionActual(){

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getContext());

        //Compruebo los permisos
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            //Comprobar que el gps esta activo
            //Si el gps esta activado muevo la camara
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.w("OBJETO LOCATION", ""+location);
                        double latitude = location.getLatitude();
                        double longitud = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitud);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                    }
                });
            } else {
                //Si el gps no esta activado muestro un toast
                Toast.makeText(getContext(), "Debes activar el GPS para recuperar tu ubicacion", Toast.LENGTH_LONG).show();
            }
        }else{
            //Si no hay permisos los pido
            Utilidades.pedirPermisosUbicacion(getActivity(), PETICION_PERMISO_LOCALIZACION);
        }
    }//FIN OBTENER UBICACION


    /**
     * Llamo a este metodo para añadir todos los listener al mapa
     */
    private void anyadirListener(){
        /*Añado el listener para cuando se hace click encima de un marcador
         * lo unico que hará será abrir la ventana de información*/
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });

        /*Añado el listener para cuando se pulse la ventana de información, lo que hará será abrir
         * una nueva actividad con la información del equipo que se ha pulsado*/
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Equipo equipo = (Equipo) marker.getTag();
                Intent i = new Intent(getActivity(), ActivityEquipo.class);
                i.putExtra(ExtrasRef.ID_EQUIPO, equipo.getIdEquipo());
                i.putExtra(ExtrasRef.NOMBRE_EQUIPO, equipo.getNombre());
                startActivity(i);
            }
        });
    }//FIN ANYADIR LISTENER

}
