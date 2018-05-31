package com.example.jjsimon.proyectodam.RecyclerViewClases;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.R;

import java.util.ArrayList;

public class RecyclerViewAdapterMensajes extends RecyclerView.Adapter<RecyclerViewAdapterMensajes.MensajesViewHolder>{
    private ArrayList<Mensaje> mensajesList;

    public RecyclerViewAdapterMensajes() {
    }

    public RecyclerViewAdapterMensajes(ArrayList<Mensaje> mensajesList) {
        this.mensajesList = mensajesList;
    }

    @NonNull
    @Override
    public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mensaje, parent, false);
        MensajesViewHolder holder = new MensajesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
        if(mensajesList.get(position).getTipoMensaje()==Mensaje.RECIBIDO)
            holder.cuerpoMensajeTV.setGravity(Gravity.LEFT);



        holder.cuerpoMensajeTV.setText(mensajesList.get(position).getCuerpoMensaje());
        holder.idConversacion = mensajesList.get(position).getIdConversacion();
        holder.tipoMensaje = mensajesList.get(position).getTipoMensaje();
    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }

    /**
     * Patron ViewHolder
     */
    public class MensajesViewHolder extends RecyclerView.ViewHolder{
        private TextView cuerpoMensajeTV;
        private String idConversacion;
        private int tipoMensaje;

        public MensajesViewHolder(View itemView) {
            super(itemView);
            cuerpoMensajeTV = (TextView) itemView.findViewById(R.id.mensaje_TV);
        }
    }//FIN DEL VIEW HOLDER
}
