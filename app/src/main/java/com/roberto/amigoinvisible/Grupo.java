package com.roberto.amigoinvisible;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Grupo  {
    private String nombre;
    private ArrayList<Miembro> miembros;

    public void añadir_Miembro(Miembro m)
    {
        if(miembros!=null)
        {
            miembros.add(m);
        }
    }
    public int getnumMiembros()
    {
        return miembros.size();
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(ArrayList<Miembro> miembros) {
        this.miembros = miembros;
    }


    public Grupo(String nombre) {
        miembros=new ArrayList<Miembro>();
        this.nombre = nombre;
    }

    public boolean buscarMiembro(String nombre)
    {
        boolean encontrado=false;
        int i=0;
        while(i<miembros.size()&&!encontrado)
        {
            if((nombre.toLowerCase()).compareTo(miembros.get(i).getNombre().toLowerCase())==0)
            {
                encontrado=true;
            }
            i++;

        }
        return encontrado;
    }


}
