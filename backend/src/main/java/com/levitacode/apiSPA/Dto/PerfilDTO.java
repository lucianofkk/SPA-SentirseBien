package com.levitacode.apiSPA.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerfilDTO {
    private String email;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String rol;
}

