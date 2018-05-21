package com.example.jjsimon.proyectodam.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Mapa extends SupportMapFragment implements OnMapReadyCallback {
    GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);
        consultarEquipos();
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {


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



    public void consultarEquipos(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FireBaseReferences.EQUIPOS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cont = 0;
                for (DataSnapshot equiposSnapshot : dataSnapshot.getChildren()){
                    cont++;
                    Equipo e = equiposSnapshot.getValue(Equipo.class);
                    String [] ubicacion = e.getUbicacion().split(",");
                    LatLng latLng = new LatLng(Double.parseDouble(ubicacion[0]),Double.parseDouble(ubicacion[1]));
                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicacion actual").draggable(true));
                    Log.w("BUCLE", e.getIdEquipo());
                }
                Log.w("BUCLE", cont+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /*
        reference.orderByChild().equalTo(true)..addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameOnList.clear();
                for (DataSnapshot gamesSnapshot : dataSnapshot.getChildren()){
                    Game game = gamesSnapshot.getValue(Game.class);
                    if(game.isStarted()) gameOnList.add(game);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    * */

}
