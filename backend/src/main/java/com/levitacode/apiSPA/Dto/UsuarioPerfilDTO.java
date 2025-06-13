package com.levitacode.apiSPA.Dto;

public class UsuarioPerfilDTO {
    private String email;
    private String nombre;
    private String apellido;
    private String dni;

    // Constructor
    public UsuarioPerfilDTO(String email, String nombre, String apellido, String dni) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
