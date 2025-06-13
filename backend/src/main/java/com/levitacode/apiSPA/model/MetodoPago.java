package com.levitacode.apiSPA.model;

/**
 * Enum que representa los diferentes métodos de pago disponibles en la aplicación.
 * Cada método de pago tiene una descripción asociada.
*/

public enum MetodoPago {
        TARJETA_CREDITO("Tarjeta de Crédito"),
        EFECTIVO("Efectivo");

        private final String descripcion;

        MetodoPago(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    
    }