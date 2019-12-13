package com.roberto.amigoinvisible;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoMiembrosGrupo extends Fragment {

    private int posicion;
    private RecyclerView recyclerView;
    private AppCompatActivity contexto;
    private AdaptadorMiembroGrupo adaptador;
    public FragmentoMiembrosGrupo(int p) {
        // Required empty public constructor
        this.posicion=p;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.fragment_miembros_grupo, container, false);
        this.contexto=(AppCompatActivity)getActivity();
        //Preparo el recyclerview
        preparar_ReciclerView(v,R.id.recycler_listado_miembros);
        return v;

    }

    private void preparar_ReciclerView(View v,int id) {

        recyclerView=(RecyclerView)v.findViewById(id);

        recyclerView.setLayoutManager(new LinearLayoutManager(contexto));
        //Añadir divisor
        recyclerView.addItemDecoration(new DividerItemDecoration(contexto,DividerItemDecoration.VERTICAL));


        adaptador= new AdaptadorMiembroGrupo(this.contexto,R.layout.elementomiembro,MainActivity.adaptador.getElementoLista(posicion).getMiembros());

        adaptador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
        adaptador.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Para conocer en que elemento se ha hecho click
                //El objeto View representa un elemento de la lista

            }
        });
        recyclerView.setAdapter(adaptador);


    }


}
