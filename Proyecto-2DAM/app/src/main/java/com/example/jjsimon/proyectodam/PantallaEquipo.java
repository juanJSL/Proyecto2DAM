package com.example.jjsimon.proyectodam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterJugador;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaEquipo extends AppCompatActivity {
    private final FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();
    private Equipo equipo;
    private String idEquipo;
    private Jugador jugador;

    private Button unirseEquipoBT;
    private TextView nombreEquipoTV;
    private TextView numJugadores;

    private ArrayList<Jugador> jugadorList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterJugador recyclerViewAdapterJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        String nombreEquipo = getIntent().getExtras().getString(ExtrasRef.NOMBRE_EQUIPO);

        consultarUsuario();

        //Enlazo las view
        unirseEquipoBT = (Button) findViewById(R.id.unirse_equipo_bt);
        numJugadores = (TextView) findViewById(R.id.equipo_numJugadoresTitle);
        nombreEquipoTV = (TextView) findViewById(R.id.equipo_nombreTV);
        nombreEquipoTV.setText(getIntent().getExtras().getString(ExtrasRef.NOMBRE_EQUIPO));

        idEquipo = getIntent().getExtras().getString(ExtrasRef.ID_EQUIPO);

        //Añado el evento onClick para el boton
        unirseEquipoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jugador.getIdEquipo()==null)
                    unirseEquipo();
                else
                    Toast.makeText(getBaseContext(), R.string.ya_tienes_equipo,
                            Toast.LENGTH_LONG).show();
            }
        });


        jugadorList  = new ArrayList<>();

        //Inicializo la RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.equipo_Recycler);

        //inicializo el adaptador le envio como parametro la lista de jugadores
        recyclerViewAdapterJugador = new RecyclerViewAdapterJugador(jugadorList);

        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(recyclerViewAdapterJugador);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        consultarJugadores();
    }

    private void consultarUsuario() {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.JUGADORES);
        reference.child(USER.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugador = dataSnapshot.getValue(Jugador.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    private void consultarJugadores(){
        //Referencia a la BD en el nodo JUGADORES
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(FireBaseReferences.JUGADORES);

        Query query = reference.orderByChild(FireBaseReferences.ID_EQUIPO).equalTo(idEquipo);
        //Busco dentro de los nodos jugadores los que tengan el id del equipo actual
        query.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numJugadores.append(" "+dataSnapshot.getChildrenCount());
                jugadorList.removeAll(jugadorList);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Jugador jugador = snapshot.getValue(Jugador.class);
                    jugadorList.add(jugador);
                }
                recyclerViewAdapterJugador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//FIN CONSULTAR JUGADORES

    /**
     * Este metodo cambia el nodo idEquipo a un usuario
     */
    private void unirseEquipo() {
        //Creo un mensaje de confirmacion
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.unirse_equipo_titulo)
                .setMessage(R.string.unirse_equipo_msj)
                .setPositiveButton(R.string.si,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Si el usuario hace click en si borro el equipo
                                DatabaseReference reference = FirebaseDatabase.getInstance()
                                        .getReference(FireBaseReferences.JUGADORES).child(USER.getUid());
                                reference.child(FireBaseReferences.ID_EQUIPO).setValue(idEquipo);
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
    }

}
