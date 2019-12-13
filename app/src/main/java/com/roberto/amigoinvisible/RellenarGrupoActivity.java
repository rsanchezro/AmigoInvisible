package com.roberto.amigoinvisible;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RellenarGrupoActivity extends AppCompatActivity {
   Toolbar barra;
    private int posicion;
    private ArrayList<Miembro> miembros;
    TextView nombre;
    TextView email;
    TextView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rellenar_grupo);

        //Añadir la flecha hacia atras
        barra=findViewById(R.id.toolbarrellenar_grupo);
        setSupportActionBar(barra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Obtengo la posicion del elemento que se ha clickado
        posicion=getIntent().getExtras().getInt("posicion");
        //Establezco el titulo
        barra.setTitle("GRUPO: "+MainActivity.adaptador.getElementoLista(posicion).getNombre());
        //Instancio el array miembros
        miembros=new ArrayList<Miembro>();
        nombre=findViewById(R.id.nombre_editText);
        email=findViewById(R.id.email_editText);
        lista=findViewById(R.id.listamiembrosTextView);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.guardar:
                MainActivity.adaptador.getElementoLista(posicion).setMiembros(miembros);
                Intent i=getIntent();
                i.putExtra("posicion",posicion);
                setResult(0,i);
                finish();
                break;
        }
        return true;
    }


    public void NuevoMiembro(View view)
    {

        if((nombre.getText().toString().compareTo("")!=0)&&(email.getText().toString().compareTo("")!=0)) {
            miembros.add(new Miembro(nombre.getText().toString(), email.getText().toString(), null));

            lista.append("Nombre: " + nombre.getText() + " email: " + email.getText() + "\n");

            if (miembros.size() == 3) {
                //Inflo el menu GUARDAR
                barra.inflateMenu(R.menu.menu_guardar);

            }
        }
        else
        {
            Toast.makeText(this,"Debes rellenar los campos Nombre y email",Toast.LENGTH_SHORT).show();
        }



    }
}
