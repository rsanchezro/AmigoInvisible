package com.roberto.amigoinvisible;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


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
        this.posicion = p;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_miembros_grupo, container, false);
        this.contexto = (AppCompatActivity) getActivity();
        //Preparo el recyclerview
        preparar_ReciclerView(v, R.id.recycler_listado_miembros);
        FloatingActionButton f = v.findViewById(R.id.fab_miembros_grupo);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrir dialogo para añadir una nuevo amigo a la lista de amigos
                final EditText nombre = new EditText(getActivity());
                nombre.setHint("Nombre");
                final EditText email = new EditText(getActivity());
                email.setHint("Email");
                LinearLayout mi_layout = new LinearLayout(getActivity());
                mi_layout.setOrientation(LinearLayout.VERTICAL);
                mi_layout.addView(nombre);
                mi_layout.addView(email);
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(getActivity());

                builderDialog.setTitle("NUEVO MIEMBRO DEL GRUPO " + MainActivity.adaptador.getElementoLista(posicion).getNombre());
                //De esta forma evitamos que al pulsar el boton de atras salgamos del cuadro de dialogo
                builderDialog.setCancelable(false);
                builderDialog.setView(mi_layout);
                builderDialog.setPositiveButton("AÑADIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Sobrescribo para no hacer nada, para validar la entrada y controllar
                        //que el usuario introduzca texto
                    }
                });
                builderDialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialogo = builderDialog.create();
                //Para evitar salir del cuadro de dialogo
                dialogo.setCanceledOnTouchOutside(false);
                dialogo.show();
                //Sobrescribo la acción OnClick del boton positivo para controlar la entrada
                dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Compruebo si el usuario ha introducido un texto
                        if (nombre.getText().toString().compareTo("") == 0) {
                            Toast.makeText(getActivity(), "DEBES INTRODUCIR NOMBRE DEL MIEMBRO", Toast.LENGTH_SHORT).show();
                        } else {
                            //COMPRUEBO SI EL Grupo ya existe
                            String n = nombre.getText().toString();

                            if (MainActivity.adaptador.getElementoLista(posicion).buscarMiembro(n)) {
                                Toast.makeText(getActivity(), "LO SIENTO PERO " + n + " YA EXISTE EN ESE GRUPO", Toast.LENGTH_SHORT).show();
                            } else {
                                //Añado un elemento a la lista
                                MainActivity.adaptador.getElementoLista(posicion).añadir_Miembro(new Miembro(n, email.getText().toString(), null));
                                // adaptador.añadir_elementoLista(new Miembro(n,email.getText().toString(),null));
                                adaptador.setElementosLista(MainActivity.adaptador.getElementoLista(posicion).getMiembros());
                                //Cierro el cuadro de dialogo
                                dialogo.dismiss();
                            }
                        }
                    }
                });
            }
        });
        return v;

    }

    private void preparar_ReciclerView(View v, int id) {

        recyclerView = (RecyclerView) v.findViewById(id);

        recyclerView.setLayoutManager(new LinearLayoutManager(contexto));
        //Añadir divisor
        recyclerView.addItemDecoration(new DividerItemDecoration(contexto, DividerItemDecoration.VERTICAL));


        adaptador = new AdaptadorMiembroGrupo(this.contexto, R.layout.elementomiembro, MainActivity.adaptador.getElementoLista(posicion).getMiembros());

        adaptador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
        adaptador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Para conocer en que elemento se ha hecho click
                //El objeto View representa un elemento de la lista

               final int pos = recyclerView.getChildAdapterPosition(v);


                //Abrir dialogo para editar un amigo en la lista de amigos


               //Preparo los datos del spinner
                ArrayList<String> nombremiembros = new ArrayList<String>();
                nombremiembros.add("");
                for (Miembro m : MainActivity.adaptador.getElementoLista(posicion).getMiembros()) {
                    if(!m.getNombre().equals(adaptador.getElementoLista(pos).getNombre())) {
                        nombremiembros.add(m.getNombre());
                    }
                }



                AlertDialog.Builder builderDialog = new AlertDialog.Builder(getActivity());

                builderDialog.setTitle("EDICION DE "+ adaptador.getElementoLista(pos));
                //De esta forma evitamos que al pulsar el boton de atras salgamos del cuadro de dialogo
                builderDialog.setCancelable(false);
                builderDialog.setView(R.layout.edicionmiembro_layout);

                builderDialog.setPositiveButton("EDITAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Sobrescribo para no hacer nada, para validar la entrada y controllar
                        //que el usuario introduzca texto
                    }
                });
                builderDialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialogo = builderDialog.create();

                final EditText nombre=(EditText)dialogo.findViewById(R.id.Nombre_miembro_ed);
                final EditText email=(EditText)dialogo.findViewById(R.id.email_miembro_ed);
                final Spinner spinner=(Spinner)dialogo.findViewById(R.id.spinner_restriccion);
                //Añado los datos al spinner
                spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nombremiembros));
                //Establezco los valores a cada elemento
                nombre.setText(adaptador.getElementoLista(pos).getNombre());
                email.setText(adaptador.getElementoLista(pos).getEmail());
                if(adaptador.getElementoLista(pos)!=null) {
                    //Tendre que averiguar que elemento de la lista es
                    int elemento_restricion = MainActivity.adaptador.getElementoLista(posicion).getMiembros().indexOf(adaptador.getElementoLista(pos).getRestriccion());
                    spinner.setSelection(elemento_restricion);

                }
                //Para evitar salir del cuadro de dialogo
                dialogo.setCanceledOnTouchOutside(false);
                dialogo.show();
                //Sobrescribo la acción OnClick del boton positivo para controlar la entrada
                dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String n=nombre.getText().toString();
                        //Guardo los cambios siempre y cuando haya nombre y no sea repetido
                        if(!n.equals(""))
                        {
                            if(MainActivity.adaptador.getElementoLista(posicion).buscarMiembro(n))
                            {
                                Toast.makeText(getActivity(),"MIEMBRO REPETIDO",Toast.LENGTH_SHORT).show();
                                dialogo.dismiss();
                            }
                            else {
                                adaptador.getElementoLista(pos).setNombre(n);
                                adaptador.getElementoLista(pos).setEmail(email.getText().toString());
                                if(spinner.getSelectedItemPosition()!=1)
                                {
                                    //Busco en la lista de miembros la posicion de este elemento
                                }
                            }
                        }



                    }


                });
            }
        });
        recyclerView.setAdapter(adaptador);
    }
}




