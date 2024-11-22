package com.MasterSolutions.appeludos.model;

public class Pet {
    private String nombre;
    private String edad;
    private String peso;
    private String ubicacion;
    private String imagen;

    public Pet() {
        // Constructor vacío requerido por Firebase
    }

    public Pet(String nombre, String edad, String peso, String ubicacion, String imagen) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
