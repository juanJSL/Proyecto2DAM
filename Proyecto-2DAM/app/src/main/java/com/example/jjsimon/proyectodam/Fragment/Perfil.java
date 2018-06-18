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
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.ALD;
import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterALD;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Permite mostrar la informacion del jugador
 */
public class Perfil extends Fragment {
    //Varibales para almacenar los datos del usuario en la consulta
    private String nick;
    private String rol;
    private String idEquipo = "FALTA LA CONSULTA";

    private ArrayList<ALD> aldList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapterALD adapterALD;

    private TextView nickTV;
    private TextView rolTV;
    private TextView equipoTV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.w("PERFIL", "Llamada onCreate");
        View fragment = inflater.inflate(R.layout.fragment_perfil, container, false);

        //Enlazo las View
        nickTV = (TextView) fragment.findViewById(R.id.nick_tv);
        rolTV = (TextView) fragment.findViewById(R.id.rol_tv);
        equipoTV= (TextView) fragment.findViewById(R.id.equipo_tv);

        //Hago la consulta a la BD para rellenar los campos
        consultarUsuario();

        return fragment;
    }


    /**
     * Este metodo hace una consulta a la base de datos
     * y rellena los campos nick, edad, equipo, rol
     */
    public void consultarUsuario(){
        Log.w("PERFIL", "Metodo consultar usuario llamado");
        //Cadena para almacenar el id del usuario actual
        String idUser=null;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Si hay un usuario actual realizo la consulta a la base de datos
        if(user!=null) {
            //Id del usuario actual
            idUser = user.getUid();

            //Referencia a la BD apuntando al nodo jugadores
            DatabaseReference bdd = FirebaseDatabase.getInstance()
                    .getReference(FireBaseReferences.JUGADORES);

            //Busco el nodo con el id del usuario actual
            bdd.child(idUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Jugador jugador = dataSnapshot.getValue(Jugador.class);
                    nick = jugador.getNick();
                    rol = jugador.getRol();
                    idEquipo = jugador.getIdEquipo();

                    nickTV.setText(nick);
                    rolTV.setText(rol);
                    if(idEquipo !=null)
                        consultarEquipo();
                    else
                        equipoTV.setText("Libre");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void consultarEquipo(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.EQUIPOS);
        reference.child(idEquipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Equipo equipo = dataSnapshot.getValue(Equipo.class);
                equipoTV.setText(equipo.getNombre());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
