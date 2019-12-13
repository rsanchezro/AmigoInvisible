package com.roberto.amigoinvisible;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private boolean actionModeactivado;
    private ArrayList<Grupo> elementoseleccionados;
    public static AdaptadorGrupoAmigos adaptador;
    private Toolbar barra;
    private RecyclerView recyclerView;
    private static final int RELLENO_MIEMBROS = 1;
    private static final int EDICION_MIEMBROS = 2;


    public ArrayList<Grupo> getElementoseleccionados() {
        return elementoseleccionados;
    }

    public void setElementoseleccionados(ArrayList<Grupo> elementoseleccionados) {
        this.elementoseleccionados = elementoseleccionados;
    }


    public boolean isActionModeactivado() {
        return actionModeactivado;
    }
    public void setActionModeactivado(boolean actionModeactivado) {
        this.actionModeactivado = actionModeactivado;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.actionModeactivado=false;
        setContentView(R.layout.activity_main);
        barra = findViewById(R.id.toolbar);
        barra.setTitle(R.string.titulo_listagrupos);
        setSupportActionBar(barra);
        elementoseleccionados=new ArrayList<Grupo>();
        preparar_ReciclerView();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir dialogo para añadir una nuevo grupo de amigos
                final EditText nombre = new EditText(MainActivity.this);
                nombre.setHint("Nombre del Grupo");
                LinearLayout mi_layout = new LinearLayout(MainActivity.this);
                mi_layout.setOrientation(LinearLayout.VERTICAL);
                mi_layout.addView(nombre);
                AlertDialog.Builder builderDialog=new AlertDialog.Builder(MainActivity.this);

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

                builderDialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialogo=builderDialog.create();
                //Para evitar salir del cuadro de dialogo
                dialogo.setCanceledOnTouchOutside(false);
                dialogo.show();
                //Sobrescribo la acción OnClick del boton positivo para controlar la entrada
                dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Compruebo si el usuario ha introducido un texto
                        if(nombre.getText().toString().compareTo("")==0)
                        {
                            Toast.makeText(MainActivity.this,"DEBES INTRODUCIR NOMBRE EN EL GRUPO DE AMIGOS",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //COMPRUEBO SI EL Grupo ya existe
                            String n=nombre.getText().toString();

                            if(adaptador.buscarGrupo(n))
                            {
                                Toast.makeText(MainActivity.this,"LO SIENTO PERO EL GRUPO DE AMIGOS "+n+" YA EXISTE",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Añado un elemento a la lista
                                adaptador.añadir_elementoLista(new Grupo(n));

                                //Cierro el cuadro de dialogo
                                dialogo.dismiss();
                            }
                        }
                    }
                });


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_delete:
                //borrar los elementos seleccionados
               adaptador.eliminar_elementos(elementoseleccionados);
                salir_actionmode();
                break;
            case R.id.item_edit:
                //Abrir dialogo para editar un Grupo
                final EditText nombre = new EditText(MainActivity.this);
                nombre.setText(elementoseleccionados.get(0).getNombre());
                LinearLayout mi_layout = new LinearLayout(MainActivity.this);
                mi_layout.setOrientation(LinearLayout.VERTICAL);
                mi_layout.addView(nombre);
                AlertDialog.Builder builderDialog=new AlertDialog.Builder(MainActivity.this);

                builderDialog.setTitle("EDICIÓN DEL GRUPO");
                //De esta forma evitamos que al pulsar el boton de atras salgamos del cuadro de dialogo
                builderDialog.setCancelable(false);
                builderDialog.setView(mi_layout);
                builderDialog.setPositiveButton("MODIFICAR", new DialogInterface.OnClickListener() {
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
                final AlertDialog dialogo=builderDialog.create();
                //Para evitar salir del cuadro de dialogo
                dialogo.setCanceledOnTouchOutside(false);
                dialogo.show();
                //Sobrescribo la acción OnClick del boton positivo para controlar la entrada
                dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Compruebo si el usuario ha introducido un texto
                        if(nombre.getText().toString().compareTo("")==0)
                        {
                            Toast.makeText(MainActivity.this,"DEBES INTRODUCIR NOMBRE EN EL GRUPO DE AMIGOS",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //COMPRUEBO SI EL Grupo ya existe
                            String n=nombre.getText().toString();
                            if(adaptador.buscarGrupo(n))
                            {
                                Toast.makeText(MainActivity.this,"LO SIENTO PERO EL GRUPO DE AMIGOS "+n+" YA EXISTE",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Modifico ese grupo
                                adaptador.modificarGrupo(elementoseleccionados.get(0), n);

                                //Cierro el cuadro de dialogo
                                dialogo.dismiss();
                            }
                        }
                    }
                });
                break;
            case android.R.id.home:
                //Salir del modo action mode
                salir_actionmode();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        //Salir del modo action mode si estoy en el
        if(actionModeactivado)
        {

            salir_actionmode();
        }
        else
        {
            super.onBackPressed();
        }


    }

    private void salir_actionmode()
    {
        elementoseleccionados.clear();
        actionModeactivado=false;
        //Limpio el menu y restablezco los estilos
        barra.setBackgroundResource(R.color.colorPrimary);
        barra.setTitleTextAppearance(this,R.style.barra);
        barra.setTitle(R.string.titulo_listagrupos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        barra.getMenu().clear();
        //notificar de los cambios en el adapatador
        adaptador.notifyDataSetChanged();
    }



    private void preparar_ReciclerView() {

        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewGrupos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Añadir divisor
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        adaptador= new AdaptadorGrupoAmigos(this,R.layout.grupo_layout);

        adaptador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //En el click largo no voy a permitir seleccionar elementos salvo la primera vez
                int posicion = recyclerView.getChildAdapterPosition(v);
                if(!actionModeactivado)
                {//Solo inflo el menu si no esta el actionmodeactivado
                    actionModeactivado = true;
                    barra.inflateMenu(R.menu.menu_action_mode);
                    //Cambio el estilo de la barra
                    barra.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    barra.setTitleTextAppearance(MainActivity.this,R.style.estiloActionpersonalizado);

                    //Seleccionar el elemento sobre el que se ha pulsado
                    seleccionar_Elemento(posicion);
                    //Actualizco el numero de elementos seleccionados
                    //Repinto el elemento
                    adaptador.notifyItemChanged(posicion);
                    //Incluir icono de flecha hacia atras
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                    actualizar_barra();
                }
                return true;
            }
        });
        adaptador.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Para conocer en que elemento se ha hecho click
                //El objeto View representa un elemento de la lista
                int codigorespuesta;
                int posicion=recyclerView.getChildAdapterPosition(v);
                if(actionModeactivado)
                {
                    seleccionar_Elemento(posicion);
                    adaptador.notifyItemChanged(posicion);
                    actualizar_barra();
                    if(elementoseleccionados.size()==0)
                    {
                        //Me salgo del action mode
                        salir_actionmode();
                    }
                }
                else
                {
                    //En funcion de si hay elementos en el grupo
                    //Abro una actividad u otra
                    Intent i;
                    int codigopeticion;
                    if(adaptador.getElementoLista(posicion).getnumMiembros()==0)
                    {
                        //Abro una actividad
                     i=new Intent(MainActivity.this,RellenarGrupoActivity.class);
                     codigopeticion=RELLENO_MIEMBROS;


                    }
                    else
                    {
                        //Abro la actividad secundaria
                       i=new Intent(MainActivity.this, ActividadSecundaria.class);
                       codigopeticion=EDICION_MIEMBROS;
                    }
                    i.putExtra("posicion",posicion);
                    startActivityForResult(i,codigopeticion);



                }
            }
        });
        recyclerView.setAdapter(adaptador);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case RELLENO_MIEMBROS:
                if(resultCode==0) {
                    adaptador.notifyItemChanged(data.getExtras().getInt("posicion"));

                }

                break;
        }


    }

    private void actualizar_barra()
    {
        Log.i("Informacion","prueba de commit");
        barra.getMenu().getItem(0).setVisible(elementoseleccionados.size()>1?false:true);
        barra.setTitle(elementoseleccionados.size()+" Elementos seleccionados...");
    }

    private void seleccionar_Elemento(int posicion)
    {
        if(elementoseleccionados.contains(adaptador.getElementoLista(posicion)))
        {
            elementoseleccionados.remove(adaptador.getElementoLista(posicion));
        }
        else
        {
            elementoseleccionados.add(adaptador.getElementoLista(posicion));
        }
    }



}
