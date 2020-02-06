package com.roberto.amigoinvisible;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class ActividadSecundaria extends AppCompatActivity {
    Toolbar barra;
    private int posicion;
    private ArrayList<Miembro> miembros;
    TextView nombre;
    TextView email;
    TextView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_secundaria);
        //Añadir la flecha hacia atras
        barra=findViewById(R.id.toolbarsecundaria);
        //Obtengo la posicion del elemento que se ha clickado
        posicion=getIntent().getExtras().getInt("posicion");

        //Establezco el titulo
        barra.setTitle("GRUPO: "+MainActivity.adaptador.getElementoLista(posicion).getNombre());
        setSupportActionBar(barra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Cargo el fragmento miembros_grupo
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedorsecundario,new FragmentoMiembrosGrupo(posicion),"FragmentoMiembros")
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent i=getIntent();
        switch (id)
        {
            case android.R.id.home:
                i.putExtra("posicion",posicion);
                setResult(1,i);
                finish();
                break;

        }
        return true;
    }
}
