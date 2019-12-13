package com.roberto.amigoinvisible;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorMiembroGrupo extends RecyclerView.Adapter<AdaptadorMiembroGrupo.Miholder> {
   private AppCompatActivity contexto;
   private ArrayList<Miembro> elementos;
    private int recursoLayout;
    private View.OnClickListener onClick;
    private View.OnLongClickListener onLongClick;

    public AdaptadorMiembroGrupo(AppCompatActivity c, int rlayout, ArrayList<Miembro> m) {
        this.contexto=c;
        this.recursoLayout=rlayout;
        elementos=m;
    }
    public Miembro getElementoLista(int posicion)
    {
        return elementos.get(posicion);
    }
    public void añadir_elementoLista(Miembro l)
    {
        this.elementos.add(l);
        notifyItemChanged((this.elementos.size()-1));
    }
    public void setOnClickListener(View.OnClickListener click)
    {
        this.onClick=click;
    }

    public void setOnLongClickListener(View.OnLongClickListener longclick)
    {
        this.onLongClick=longclick;
    }
    @NonNull
    @Override
    public AdaptadorMiembroGrupo.Miholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        try {
            v = contexto.getLayoutInflater().inflate(this.recursoLayout, parent, false);

        }
        catch (Exception e)
        {

        }
        //Establezco los escuchadores a la vista
        v.setOnClickListener(this.onClick);
        v.setOnLongClickListener(this.onLongClick);
        return new Miholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMiembroGrupo.Miholder holder, int position) {
        holder.elemento=this.elementos.get(position);
        //Asocio los datos de los elementos visuales
        holder.nombre.setText(holder.elemento.getNombre());
        holder.email.setText(holder.elemento.getEmail());

        if(holder.elemento.getRestriccion()!=null) {
            holder.restriccion.setText(holder.elemento.getRestriccion().getNombre());
        }
        else {
            holder.linear.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public class Miholder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView email;
        public TextView restriccion;
        public LinearLayout linear;
        public Miembro elemento;

        public View v;
        public Miholder(@NonNull View itemView) {
            super(itemView);
            this.v=itemView;
            this.nombre=v.findViewById(R.id.nombremiembro_txtview);
            this.email=v.findViewById(R.id.emailmiembro_txtView);
            this.restriccion=v.findViewById(R.id.restriccionmiembro_txtView);
            this.linear=v.findViewById(R.id.restriccion_layout);

        }
    }
}
