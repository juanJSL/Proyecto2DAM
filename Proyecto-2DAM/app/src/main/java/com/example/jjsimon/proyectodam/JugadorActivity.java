package com.example.jjsimon.proyectodam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Equipo;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JugadorActivity extends AppCompatActivity {

    private TextView nickTV;
    private TextView rolTV;
    private TextView nombreEquipoTV;
    private String idJugador;
    private String idEquipo;
    private Button enviarPrivadoBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        //Enlazo las view
        enviarPrivadoBT = findViewById(R.id.enviar_privado_bt);
        nickTV = findViewById(R.id.nick_tv);
        rolTV = findViewById(R.id.rol_tv);
        nombreEquipoTV = findViewById(R.id.equipo_tv);

        nickTV.setText(getIntent().getExtras().getString(ExtrasRef.NICK_JUGADOR));
        rolTV.setText(getIntent().getExtras().getString(ExtrasRef.ROL_JUGADOR));
        idJugador = getIntent().getExtras().getString(ExtrasRef.ID_JUGADOR);
        consultarIdEquipo();


        enviarPrivadoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String emisor = user.getUid();
                String destinatario = idJugador;
                Intent i = new Intent(JugadorActivity.this, MDActivity.class);
                i.putExtra(ExtrasRef.ID_EMISOR, emisor);
                i.putExtra(ExtrasRef.ID_DESTINATARIO, destinatario);
                startActivity(i);
            }
        });
    }

    private void consultarIdEquipo(){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.JUGADORES);
        reference.child(idJugador).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Jugador jugador = dataSnapshot.getValue(Jugador.class);
                idEquipo = jugador.getIdEquipo();
                consultarEquipo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void consultarEquipo(){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference(FireBaseReferences.EQUIPOS);
        reference.child(idEquipo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Equipo equipo = dataSnapshot.getValue(Equipo.class);
                nombreEquipoTV.setText(equipo.getNombre());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
