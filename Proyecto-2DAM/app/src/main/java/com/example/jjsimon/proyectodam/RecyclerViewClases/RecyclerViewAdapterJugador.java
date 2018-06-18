package com.example.jjsimon.proyectodam.RecyclerViewClases;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.JugadorActivity;
import com.example.jjsimon.proyectodam.R;
import com.example.jjsimon.proyectodam.Referencias.ExtrasRef;

import java.util.ArrayList;

public class RecyclerViewAdapterJugador
        extends RecyclerView.Adapter<RecyclerViewAdapterJugador.JugadoresViewHolder> {
    private ArrayList<Jugador> jugadorList;


    /**
     * Constructor que recibe como parametro una lista de jugadores que será la que se
     * muestre en el RecyclerView
     * @param jugadorList
     */
    public RecyclerViewAdapterJugador(ArrayList<Jugador> jugadorList) {
        this.jugadorList = jugadorList;
    }

    @NonNull
    @Override
    public JugadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_miembro,parent,false);
        final JugadoresViewHolder holder = new JugadoresViewHolder(item);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext() ,JugadorActivity.class);
                i.putExtra(ExtrasRef.ID_JUGADOR, holder.idJugador);
                i.putExtra(ExtrasRef.NICK_JUGADOR, holder.nickJugador.getText().toString());
                i.putExtra(ExtrasRef.ROL_JUGADOR, holder.rol.getText().toString());
                v.getContext().startActivity(i);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JugadoresViewHolder holder, int position) {
        Jugador jugador = jugadorList.get(position);
        holder.bindJugador(jugador);
    }

    @Override
    public int getItemCount() {
        return jugadorList.size();
    }


    /**
     * Clase que exitende de la clase ViewHolder para
     * cargarr las CardView
     */
    public static class JugadoresViewHolder extends RecyclerView.ViewHolder{

        //View que forman la CardView
        private TextView mailJugador;
        private TextView nickJugador;
        private TextView rol;
        private String idJugador;

        public JugadoresViewHolder(View itemView) {
            super(itemView);
            mailJugador = (TextView) itemView.findViewById(R.id.card_nombre_jugador);
            nickJugador = (TextView) itemView.findViewById(R.id.card_nick_jugador);
            rol = (TextView) itemView.findViewById(R.id.card_rol_jugador);

        }

        public void bindJugador(Jugador jugador){
            mailJugador.setText(jugador.getMail());
            nickJugador.setText(jugador.getNick());
            rol.setText(jugador.getRol());
            idJugador = jugador.getIdJugador();
        }
    }
}
