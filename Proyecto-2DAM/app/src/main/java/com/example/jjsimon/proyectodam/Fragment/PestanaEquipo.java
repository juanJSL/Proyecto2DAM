package com.example.jjsimon.proyectodam.Fragment;


        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.constraint.ConstraintLayout;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;

        import com.example.jjsimon.proyectodam.Clases.Equipo;
        import com.example.jjsimon.proyectodam.Clases.Jugador;
        import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
        import com.example.jjsimon.proyectodam.R;
        import com.example.jjsimon.proyectodam.RecyclerViewClases.RecyclerViewAdapterJugador;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

        import static android.app.Activity.RESULT_OK;

/**
 * Muestra la informacion del equipo al que pertenece el jugador
 * si no lo tiene muestra un mensaje de informacion
 */
public class PestanaEquipo extends Fragment {

    private ConstraintLayout constraintLayout;
    private TextView mensajeSinEquipo;

    private Equipo equipo;
    private String idEquipo;
    private Jugador jugador;
    private TextView nombreEquipoET;
    private Button dejarEquipoBT;
    private final FirebaseUser USER = FirebaseAuth.getInstance().getCurrentUser();

    private ArrayList<Jugador> jugadoresList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapterJugador recyclerViewAdapterJugador;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentEquipo = inflater.inflate(R.layout.fragment_equipo, container, false);

        //Enlazo las view
        dejarEquipoBT = (Button) fragmentEquipo.findViewById(R.id.dejar_equipo_bt);
        nombreEquipoET = (TextView) fragmentEquipo.findViewById(R.id.wequipo_nombreTV);
        recyclerView = (RecyclerView) fragmentEquipo.findViewById(R.id.we_equipoRecycler);
        constraintLayout = (ConstraintLayout) fragmentEquipo.findViewById(R.id.fragmentEquipoXML);
        mensajeSinEquipo = (TextView) fragmentEquipo.findViewById(R.id.sin_equipo_tv);
        mensajeSinEquipo.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);

        //Añado la funcion al boton dejar equipo
        dejarEquipoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dejarEquipo();
            }
        });


        //Instancio la lista
        jugadoresList = new ArrayList<>();

        //inicializo el adaptador le envio como parametro la lista de jugadores
        recyclerViewAdapterJugador = new RecyclerViewAdapterJugador(jugadoresList);

        //Enlazo el recyclerView con el adaptador
        recyclerView.setAdapter(recyclerViewAdapterJugador);

        //Indico el tipo de layout que va a utilizar la recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        //Realizo las consultas necesarias a la BD para completar la informacion
        consultarJugador();

        return fragmentEquipo;
    }

    /**
     * Este metodo borra el nodo idEquipo del usuario actual
     */
    private void dejarEquipo() {
        //Creo un mensaje de confirmacion
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.dejar_equipo_titulo)
                .setMessage(R.string.dejar_equipo_msj)
                .setPositiveButton(R.string.si,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Si el usuario hace click en si borro el equipo
                                DatabaseReference reference;
                                reference = FirebaseDatabase
                                        .getInstance()
                                        .getReference(FireBaseReferences.JUGADORES)
                                        .child(USER.getUid());
                                reference
                                        .child(FireBaseReferences.ID_EQUIPO)
                                        .setValue(null);
                            }
                        })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
        builder.create().show();
    }


    /**
     * Consulto los datos del usuario actual para obtener el id de su equipo
     */
    public void consultarJugador() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference(FireBaseReferences.JUGADORES);

        databaseReference.child(USER.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jugador = dataSnapshot.getValue(Jugador.class);
                idEquipo = jugador.getIdEquipo();
                if (idEquipo != null && !idEquipo.equals("")) {
                    cargarDatosEquipo();
                    cargarJugadores();
                    mensajeSinEquipo.setVisibility(View.INVISIBLE);
                    constraintLayout.setVisibility(View.VISIBLE);
                } else {
                    mensajeSinEquipo.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }//FIN CONSULTAR JUGADOR


    /**
     * Este metodo hace una consulta a la base de datos y obtiene
     * los datos del equipo al que pertenece el usuario
     */
    public void cargarDatosEquipo() {
        //Referencia a la BD apuntando al nodo EQUIPOS
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.EQUIPOS);

        //Busco el nodo con el id del equipo del usuario
        reference.child(idEquipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                equipo = dataSnapshot.getValue(Equipo.class);
                nombreEquipoET.setText(equipo.getNombre());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }// FIN CARGAR DATOS EQUIPO


    /**
     * Este metodo realiza una consulta a la base de datos y rellena la lista de jugadores
     * con la que se cargaran las CardView
     */
    private void cargarJugadores() {
        //Referencia a la BD en el nodo JUGADORES
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);

        Query query = reference.orderByChild(FireBaseReferences.ID_EQUIPO).equalTo(idEquipo);
        //Busco dentro de los nodos jugadores los que tengan el id del equipo actual
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Vacio la lista de jugadores
                jugadoresList.removeAll(jugadoresList);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Jugador j = snapshot.getValue(Jugador.class);
                    jugadoresList.add(j);
                }
                recyclerViewAdapterJugador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }//FIN CARGAR JUGADORES

}
