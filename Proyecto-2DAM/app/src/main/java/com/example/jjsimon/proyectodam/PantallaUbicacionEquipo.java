package com.example.jjsimon.proyectodam;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
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

public class PantallaUbicacionEquipo extends FragmentActivity
        implements OnMapReadyCallback,
                    GoogleApiClient.ConnectionCallbacks,
                    GoogleApiClient.OnConnectionFailedListener{

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private GoogleApiClient apiClient;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates;

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

        pruebaDani();
        //obtenerPosicionActual();
        //prueba();


    }


   /* public void obtenerPosicionActual() {

        Log.w("UBICACION", "Metodo llamado");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();


        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitud = location.getLongitude();


        Log.w("UBICACION", "Latitud "+latitude);
        Log.w("UBICACION", "longitud "+longitud);
    }
*/



    private void prueba(){
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();


    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services
        Log.d("LOCALIZACION", "onConnected");




        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            Log.w("LOCALIZACION", ""+lastLocation);
           // updateUI(lastLocation);
           // double latitude = lastLocation.getLatitude();
           // double longitud = lastLocation.getLongitude();




            //Log.w("LOCALIZACION", "Latitud "+latitude);
            //Log.w("LOCALIZACION", "longitud "+longitud);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("LOCALIZACION", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.
        Log.d("LOCALIZACION", "onConnectionFailed");
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);

                Log.w("LOCALIZACION", ""+lastLocation);
/*
                double latitude = lastLocation.getLatitude();
                double longitud = lastLocation.getLongitude();


                Log.w("LOCALIZACION", "Latitud "+latitude);
                Log.w("LOCALIZACION", "longitud "+longitud);
*/
            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e("LOCALIZACIONS", "Permiso denegado");
            }
        }
    }








/*
    private void initGooGleApiClient(){
        if(mGoogleApiClient !=null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();


        }
    }



    @SuppressLint("RestrictedApi")
    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);//Crear constante para el tiempo
        mLocationRequest.setFastestInterval(5000);//Crear constante para el tiempo
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }


    private void startLocationUpdates(){
        if(mGoogleApiClient!=null && mGoogleApiClient.isConnected() && !mRequestingLocationUpdates){
            LocationServices.
        }
        mRequestingLocationUpdates = true;
    }

    private void stopLocationUpdates(){
        mRequestingLocationUpdates = false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("LOCALIZACION", "onConnected");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("LOCALIZACION", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("LOCALIZACION", "onConnectionFailed");
    }
    */


    private void pruebaDani(){
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
                    }
                });
            } else {
                //Comprobar permisos
                Log.w("PERMISOS", "No tiene permisos");
            }
        }
    }
}
