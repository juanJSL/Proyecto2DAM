package com.example.jjsimon.proyectodam.RecyclerViewClases;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.example.jjsimon.proyectodam.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.JugadoresViewHolder> {
    private ArrayList<Jugador> jugadorList;


    /**
     * Constructor que recibe como parametro una lista de jugadores que será la que se
     * muestre en el RecyclerView
     * @param jugadorList
     */
    public RecyclerViewAdapter(ArrayList<Jugador> jugadorList) {
        this.jugadorList = jugadorList;
    }

    @NonNull
    @Override
    public JugadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_miembro,parent,false);
        JugadoresViewHolder jugadoresViewHolder = new JugadoresViewHolder(item);
        return jugadoresViewHolder;
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
        private TextView nombreJugador;
        private TextView nickJugador;
        private TextView rol;
        private ImageView fotoJugador;

        public JugadoresViewHolder(View itemView) {
            super(itemView);
            nombreJugador = (TextView) itemView.findViewById(R.id.card_nombre_jugador);
            nickJugador = (TextView) itemView.findViewById(R.id.card_nick_jugador);
            rol = (TextView) itemView.findViewById(R.id.card_rol_jugador);
            fotoJugador = (ImageView) itemView.findViewById(R.id.card_img_jugador);

        }

        public void bindJugador(Jugador jugador){
            nombreJugador.setText(jugador.getMail());
            nickJugador.setText(jugador.getNick());
            rol.setText(jugador.getRol());
            //Investigar de como coger la imagen
            //fotoJugador.setImageIcon(jugador.getFotoPerfil());
        }
    }
}
