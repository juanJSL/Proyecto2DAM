package com.example.jjsimon.proyectodam.Fragment;


import android.Manifest;
import android.content.Context;
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

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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
    GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMapReady(GoogleMap map) {

        CameraUpdate camUpd;
        CameraPosition camPos;
        int vistaActual=0;

        mMap = map;


        // Add a marker in Sydney and move the camera
        LatLng initLocation = new LatLng(36.840835, -2.471172);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initLocation));
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        consultarEquipos();
        ubicacionActual();

    }



    public void consultarEquipos(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final ArrayList<Equipo> arrayList = new ArrayList<>();
        reference.child(FireBaseReferences.EQUIPOS).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Recorro el resultado de la consulta
                for (DataSnapshot equiposSnapshot : dataSnapshot.getChildren()){
                    //Guardo los datos en un objeto del tipo equipo
                    Equipo equipo = equiposSnapshot.getValue(Equipo.class);

                    //Pongo un marcador con los datos del objeto equipo
                    String [] ubicacion = equipo.getUbicacion().split(",");
                    LatLng latLng = new LatLng(Double.parseDouble(ubicacion[0]),Double.parseDouble(ubicacion[1]));
                    //Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(equipo.getNombre()).draggable(true).snippet("Snippet"));
                    pruebaiconGenerator(equipo, latLng);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//FIN CONSULTAR EQUIPOS

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pruebaiconGenerator(Equipo e, LatLng latLng){


        TextView text = new TextView(getContext());
        text.setText(e.getNombre());
        IconGenerator generator = new IconGenerator(getContext());

        generator.setBackground(getContext().getDrawable(R.drawable.amu_bubble_mask));
        generator.setContentView(text);
        generator.setColor(Color.WHITE);
        Bitmap icon = generator.makeIcon();

        MarkerOptions tp = new MarkerOptions().position(latLng).title(e.getNombre()).icon(BitmapDescriptorFactory.fromBitmap(icon));
        mMap.addMarker(tp).showInfoWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ubicacionActual();
                Log.e("PERMISOS", "Permiso concedido");
            } else {
                Toast.makeText(getContext(), "Debes dar permisos para poder seleccionar la ubicacion", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Este metodo obtiene la ubicacion actual del usuario y la guarda en una variable
     */
    private void ubicacionActual(){

        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getContext());

        //Compruebo los permisos
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Comprobar que el gps esta activo
            LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
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
            pedirPermisos();
        }
    }//FIN OBTENER UBICACION


    public void pedirPermisos(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);
    }
}
