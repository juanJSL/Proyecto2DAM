package com.example.jjsimon.proyectodam.RecyclerViewClases;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jjsimon.proyectodam.Clases.Mensaje;
import com.example.jjsimon.proyectodam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RecyclerViewAdapterMensajes extends RecyclerView.Adapter<RecyclerViewAdapterMensajes.MensajesViewHolder>{
    private ArrayList<Mensaje> mensajesList;

    public RecyclerViewAdapterMensajes() {
        mensajesList = new ArrayList<>();
    }

    public RecyclerViewAdapterMensajes(ArrayList<Mensaje> mensajesList) {
        this.mensajesList = mensajesList;
    }

    public void addMensaje(Mensaje mensaje){
        mensajesList.add(mensaje);
        Log.w("scroll", mensajesList.size()+"");
        notifyItemInserted(mensajesList.size());
    }

    @NonNull
    @Override
    public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mensaje, parent, false);
        MensajesViewHolder holder = new MensajesViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {
        //
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Se debe aplicar estilo en ambos casos de lo contrario al hacer scroll coge el stilo de la card que esta reciclando
        if(mensajesList.get(position).getIdEmisor().equals(user.getUid())) {
            holder.cuerpoMensajeTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.cuerpoMensajeTV.setTextColor(Color.RED);
            //CardView c = new CardView();
        }else
            holder.cuerpoMensajeTV.setTextColor(Color.BLUE);



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
