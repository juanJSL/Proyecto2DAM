package com.example.jjsimon.proyectodam.RecyclerViewClases;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.ALD;
import com.example.jjsimon.proyectodam.R;

import java.util.ArrayList;

public class RecyclerViewAdapterALD extends  RecyclerView.Adapter<RecyclerViewAdapterALD.ALDViewHolder>{
    private ArrayList<ALD> aldLIst;

    /**
     * Constructor que recibe como parametro una lista de ALD que será la que se
     * muestre en el RecyclerView
     * @param aldLIst
     */
    public RecyclerViewAdapterALD(ArrayList<ALD> aldLIst) { this.aldLIst = aldLIst; }

    @NonNull
    @Override
    public ALDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ald,parent,false);
        ALDViewHolder aldViewHolder = new ALDViewHolder(item);
        return aldViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ALDViewHolder holder, int position) {
        ALD ald = aldLIst.get(position);
        holder.bindALD(ald);
    }

    @Override
    public int getItemCount() {
        return aldLIst.size();
    }

    /**
     * Clase que exitende de la clase ViewHolder para poder crear las CardView
     */
    public static class ALDViewHolder extends RecyclerView.ViewHolder{

        //View que forman la CardView
        private TextView marcaModelo;
        private TextView fps;
        private TextView rol;
        private ImageView fotoReplica;

        public ALDViewHolder(View itemView) {
            super(itemView);
            marcaModelo = (TextView) itemView.findViewById(R.id.card_marca_modelo);
            fps = (TextView) itemView.findViewById(R.id.card_fps_ald);
            rol = (TextView) itemView.findViewById(R.id.card_rol_ald);
            fotoReplica = (ImageView) itemView.findViewById(R.id.card_img_ald);

        }

        public void bindALD(ALD ald){
            marcaModelo.setText(ald.getModelo());
            fps.setText(ald.getFps()+"");
            rol.setText(ald.getRol());
            //Investigar de como coger la imagen
            //fotoReplica.setImageIcon(jugador.getFotoPerfil());
        }
    }
}