package com.roberto.amigoinvisible;

public class Miembro {
    private String nombre;
    private String email;
    private Miembro restriccion;

    public Miembro(String nombre, String email, Miembro restriccion) {
        this.nombre = nombre;
        this.email = email;
        this.restriccion = restriccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Miembro getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(Miembro restriccion) {
        this.restriccion = restriccion;
    }



}
