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
        //nickTV = (TextView)
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

/***********************************COMENTADO PARA UNA FUTURA AMPLIACION***************************************
        //Inicializo la lista de ALD
        inicializarLista();

        //Inicializo la RecyclerView
        recyclerView = (RecyclerView) fragment.findViewById(R.id.perfilRecycler);

        //inicializo el adaptador le envio como parametro la lista de jugadores
        adapterALD = new RecyclerViewAdapterALD(aldList);
        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(adapterALD);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
**********************************************************************************************************************/
        return fragment;
    }


    /**
     * Este metodo hace una consulta a la base de datos y rellena los campos nick, edad, equipo, rol
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
            DatabaseReference bdd = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);

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
                    Log.w("EQUIPO", ""+ jugador.getIdEquipo());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("PERFIL", "Cancelled");
                }
            });
        }
    }



    /**
     * Este metodo realiza una consulta a la base de datos y rellena la lista de ALD
     * con la que se cargaran las CardView
     */
    private void inicializarLista() {
        //ALD de prueba despues se debe sustituir por una consulta a la BD
        ALD a1 = new ALD(1, "G36c", "Fusilero", 350);
        ALD a2 = new ALD(2, "HK416", "Fusilero", 350);
        ALD a3 = new ALD(3, "L96", "Sniper", 500);
        ALD a4 = new ALD(4, "P90", "Fusilero", 350);
        ALD a5 = new ALD(5, "SPR", "DMR", 450);

        aldList.add(a1);
        aldList.add(a2);
        aldList.add(a3);
        aldList.add(a4);
        aldList.add(a5);
    }


    private void consultarEquipo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.EQUIPOS);
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
