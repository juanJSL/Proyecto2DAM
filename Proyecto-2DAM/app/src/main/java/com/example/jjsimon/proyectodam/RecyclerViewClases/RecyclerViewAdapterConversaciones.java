package com.example.jjsimon.proyectodam.RecyclerViewClases;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Conversacion;
import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.FireBase.FireBaseReferences;
import com.example.jjsimon.proyectodam.MDActivity;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by diurno on 29/05/18.
 */

public class RecyclerViewAdapterConversaciones extends RecyclerView.Adapter<RecyclerViewAdapterConversaciones.ConversacionesViewHolder>{
    private ArrayList<Conversacion> conversacionesList;

    public RecyclerViewAdapterConversaciones() {
    }

    public RecyclerViewAdapterConversaciones(ArrayList<Conversacion> conversacionesList) {
        this.conversacionesList = conversacionesList;
    }

    public void addConversacion(Conversacion conversacion){
        conversacionesList.add(conversacion);
        notifyItemInserted(conversacionesList.size());
    }

    @NonNull
    @Override
    public ConversacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_conversacion, parent, false);
        final ConversacionesViewHolder holder = new ConversacionesViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = holder.nickEmisor.getText().toString();
                Intent i = new Intent(v.getContext() ,MDActivity.class);
                i.putExtra(ExtrasRef.ID_EMISOR, holder.idEmisor);
                i.putExtra(ExtrasRef.ID_DESTINATARIO, holder.idDestinatario);
                v.getContext().startActivity(i);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversacionesViewHolder holder, int position) {
        holder.bindConversacion(conversacionesList.get(position));
    }

    @Override
    public int getItemCount() {
        return conversacionesList.size();
    }

    /**
     * Patron ViewHolder
     */
    public static class ConversacionesViewHolder extends RecyclerView.ViewHolder{

        //View que forman la CardView
        private TextView nickEmisor;
        private ImageView bolita;
        private String idEmisor;
        private String idDestinatario;

        public ConversacionesViewHolder(View itemView) {
            super(itemView);
            nickEmisor = (TextView) itemView.findViewById(R.id.nick_emisor_TV);
            bolita = (ImageView) itemView.findViewById(R.id.bolitaNotificacion);
        }

        public void bindConversacion(Conversacion conversacion){
            nickEmisor.setText(conversacion.getIdDestinatario());
            idEmisor = conversacion.getIdEmisor();
            idDestinatario = conversacion.getIdDestinatario();
            consultarNick(conversacion.getIdDestinatario());
            if(conversacion.getTieneMensajes()) {
                bolita.setVisibility(View.VISIBLE);
            }else {
                bolita.setVisibility(View.INVISIBLE);
            }
        }

        public void consultarNick(String idUsuer){
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference(FireBaseReferences.JUGADORES);
            reference.child(idUsuer).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Jugador j = dataSnapshot.getValue(Jugador.class);
                    nickEmisor.setText(j.getNick());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }//FIN DEL VIEW HOLDER
}
