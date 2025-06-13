package com.levitacode.apiSPA.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int duracion; // Duración en minutos
    private String nombre;
    private String tipo; // categoria
    private String descripcion;
    private Double precio;


    // Getters
public Long getId() {
    return id;
}

public String getNombre() {
    return nombre;
}

public String getTipo() {
    return tipo;
}

public String getDescripcion() {
    return descripcion;
}

public Double getPrecio() {
    return precio;
}
}
