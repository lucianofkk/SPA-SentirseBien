package com.levitacode.apiSPA.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TurnoDTO {
    private Integer clienteId;
    private Integer profesionalId;
    private Integer servicioId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;       // Aquí estado como String
    private String metodoPago;   // Aquí metodoPago como String
    private boolean pagado;
    private boolean pagoWeb;
    private Double monto;
    private String detalle;

    // Getters y setters

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public Integer getProfesionalId() { return profesionalId; }
    public void setProfesionalId(Integer profesionalId) { this.profesionalId = profesionalId; }

    public Integer getServicioId() { return servicioId; }
    public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public boolean isPagado() { return pagado; }
    public void setPagado(boolean pagado) { this.pagado = pagado; }

    public boolean isPagoWeb() { return pagoWeb; }
    public void setPagoWeb(boolean pagoWeb) { this.pagoWeb = pagoWeb; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}
