package com.MasterSolutions.appeludos.model;

import com.google.firebase.firestore.PropertyName;

public class Pet {
    private String nombre;
    private String especie;
    private String edad;
    private String tamaño;
    private String peso;
    private String carácter;
    private String ubicacion;
    private String imagen;

    public Pet() {
        // Constructor vacío requerido por Firebase
    }

    public Pet(String nombre, String especie, String edad, String tamaño, String peso, String carácter, String ubicacion, String imagen) {
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.tamaño = tamaño;
        this.peso = peso;
        this.carácter = carácter;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @PropertyName("especie")
    public String getEspecie() {
        return especie;
    }

    @PropertyName("especie")
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    @PropertyName("edad")
    public String getEdad() {
        return edad;
    }

    @PropertyName("edad")
    public void setEdad(String edad) {
        this.edad = edad;
    }

    @PropertyName("tamaño")
    public String getTamaño() {
        return tamaño;
    }

    @PropertyName("tamaño")
    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    @PropertyName("peso")
    public String getPeso() {
        return peso;
    }

    @PropertyName("peso")
    public void setPeso(String peso) {
        this.peso = peso;
    }

    @PropertyName("carácter")
    public String getCarácter() {
        return carácter;
    }

    @PropertyName("carácter")
    public void setCarácter(String carácter) {
        this.carácter = carácter;
    }

    @PropertyName("ubicación")
    public String getUbicacion() {
        return ubicacion;
    }

    @PropertyName("ubicación")
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @PropertyName("imagen")
    public String getImagen() {
        return imagen;
    }

    @PropertyName("imagen")
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
