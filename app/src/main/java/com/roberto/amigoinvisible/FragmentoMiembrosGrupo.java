package com.roberto.amigoinvisible;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


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
        FloatingActionButton f=v.findViewById(R.id.fab_miembros_grupo);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrir dialogo para añadir una nuevo amigo a la lista de amigos
                final EditText nombre = new EditText(MainActivity.this);
                nombre.setHint("Nombre");
                LinearLayout mi_layout = new LinearLayout(MainActivity.this);
                mi_layout.setOrientation(LinearLayout.VERTICAL);
                mi_layout.addView(nombre);
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(MainActivity.this);

                builderDialog.setTitle("NUEVO GRUPO DE AMIGUETES");
                //De esta forma evitamos que al pulsar el boton de atras salgamos del cuadro de dialogo
                builderDialog.setCancelable(false);
                builderDialog.setView(mi_layout);
                builderDialog.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Sobrescribo para no hacer nada, para validar la entrada y controllar
                        //que el usuario introduzca texto
                    }
                });
            }
        });
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
