package com.example.jjsimon.proyectodam.RecyclerViewClases;

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
import com.example.jjsimon.proyectodam.R;

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

    @NonNull
    @Override
    public ConversacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_conversacion, parent, false);
        ConversacionesViewHolder holder = new ConversacionesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversacionesViewHolder holder, int position) {
        holder.nickEmisor.setText(conversacionesList.get(position).getNickEmisor());
    }

    @Override
    public int getItemCount() {
        return conversacionesList.size();
    }

    public static class ConversacionesViewHolder extends RecyclerView.ViewHolder{

        //View que forman la CardView
        private TextView nickEmisor;

        public ConversacionesViewHolder(View itemView) {
            super(itemView);
            nickEmisor = (TextView) itemView.findViewById(R.id.nick_emisor_TV);

        }
    }//FIN DEL VIEW HOLDER
}
