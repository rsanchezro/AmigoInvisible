package com.roberto.amigoinvisible;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Grupo implements Serializable {
    private String nombre;
    private ArrayList<Miembro> miembros;

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


}
