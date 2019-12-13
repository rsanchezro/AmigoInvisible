package com.roberto.amigoinvisible;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class AdaptadorGrupoAmigos extends RecyclerView.Adapter<AdaptadorGrupoAmigos.Miholder> {
    private static ArrayList<Grupo> elementos;
    private MainActivity ctx;
    private int recursoLayout;
    private View.OnClickListener onClick;
    private View.OnLongClickListener onLongClick;


    public Grupo getElementoLista(int posicion)
    {
        return elementos.get(posicion);
    }
    public void añadir_elementoLista(Grupo l)
    {
        this.elementos.add(l);
        notifyItemChanged((this.elementos.size()-1));
    }

    public AdaptadorGrupoAmigos(AppCompatActivity c, int rlayout)
    {


        this.ctx=(MainActivity)c;
        this.recursoLayout=rlayout;
        elementos=new ArrayList<Grupo>();

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
    public Miholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Este metodo se invocará tantas veces como el número de elementos que se visualicen de forma simultanea
        //Inflo la vista
        View v=null;
        try {
          v = ((AppCompatActivity)ctx).getLayoutInflater().inflate(this.recursoLayout, parent, false);

        }
        catch (Exception e)
        {
            Log.i("Informacion",e.getMessage().toString());
        }
        //Establezco los escuchadores a la vista
        v.setOnClickListener(this.onClick);
        v.setOnLongClickListener(this.onLongClick);



        //Retorno una instancia de miholder
        return new Miholder(v);
    }

    public void eliminar_elementos(ArrayList<Grupo> a)
    {

        for (Grupo elemento:a
             ) {
            elementos.remove(elemento);
        }
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull final Miholder holder, int position) {
        Log.i("Informacion","Metodo bind"+position);
        //Este metodo se invocara tantas veces como elementos se vayan visualizando
        //Vinculo los datos del alumno que voy a tratar
        holder.elemento=this.elementos.get(position);
        //Asocio los datos de los elementos visuales
        holder.nombre.setText(holder.elemento.getNombre());
        holder.num_miembros.setText(holder.elemento.getnumMiembros()+"");

        holder.vista.setBackgroundResource(R.color.colorFondoElemento);
        //Cambio el color de fondo en funcion de si el elemento esta seleccionado o

        if(this.ctx.isActionModeactivado()) {
            if (this.ctx.getElementoseleccionados().contains(holder.elemento)) {
                holder.vista.setBackgroundResource(R.color.colorFondoElementoSeleccionado);
            }
        }

    }

    public void modificarGrupo(Grupo g,String n)
    {
        g.setNombre(n);
        notifyItemChanged(elementos.indexOf(g));
    }
    public boolean buscarGrupo(String nombre)
    {
        boolean encontrado=false;
        int i=0;
        while(i<elementos.size()&&!encontrado)
        {
            if((nombre.toLowerCase()).compareTo(elementos.get(i).getNombre().toLowerCase())==0)
            {
                encontrado=true;
            }
            i++;

        }
        return encontrado;
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }


    public class Miholder extends ViewHolder{
        public View vista;
        public TextView nombre;
        public TextView num_miembros;
        public Grupo elemento;
        public Miholder(View v){
            super(v);
            this.vista=v;
            this.nombre=this.vista.findViewById(R.id.nombremiembro_txtview);
            this.num_miembros=this.vista.findViewById(R.id.restriccionmiembro_txtView);



        }

    }
}
