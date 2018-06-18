package com.example.jjsimon.proyectodam.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsimon.proyectodam.Clases.Conversacion;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterConversaciones;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Pantalla en la que se muestran las conversaiones que abiertas que tiene un usuario
 */
public class Chat extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Conversacion> conversacionesList;
    private RecyclerViewAdapterConversaciones adapter;
    private View fragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        fragment = inflater.inflate(R.layout.fragment_chat, container, false);

        //Enlazo la RecyclerView
        recyclerView =(RecyclerView) fragment.findViewById(R.id.recycler_conversaciones);

        conversacionesList = new ArrayList<>();

        adapter = new RecyclerViewAdapterConversaciones(conversacionesList);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //recuperarConversaciones();

        anyadirChanged();

        return fragment;
    }

    //Añade una consulta que refresca las conversaciones si se han producido cambios
    private void anyadirChanged() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference conversacionesRef;
        conversacionesRef = FirebaseDatabase.getInstance().getReference(FireBaseReferences.CONVERSACIONES);
        Query query = conversacionesRef;

        query.orderByChild(FireBaseReferences.ID_EMISOR).equalTo(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Conversacion c = dataSnapshot.getValue(Conversacion.class);
                adapter.addConversacion(c);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //conversacionesList.removeAll(conversacionesList);
                //adapter.notifyDataSetChanged();
                //recuperarConversaciones();
                Conversacion c = dataSnapshot.getValue(Conversacion.class);
                //Obtengo la posicion del elemento que ha cambiado
                int position;
                position=conversacionesList.indexOf(c);
                //Borro el elemento de la lista
                conversacionesList.remove(position);
                //Añado el elemento ya cambiado a la lista y lo notifico
                adapter.addConversacionAtPosition(c,position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void recuperarConversaciones() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference conversacionesRef;
        conversacionesRef = FirebaseDatabase.getInstance().getReference(FireBaseReferences.CONVERSACIONES);
        Query query = conversacionesRef;

        query.orderByChild(FireBaseReferences.ID_EMISOR).equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w("CONSULTA", "data ch");
                for (DataSnapshot conversacionSnapshot : dataSnapshot.getChildren()){
                    Conversacion c = conversacionSnapshot.getValue(Conversacion.class);
                    adapter.addConversacion(c);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
