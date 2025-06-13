package com.levitacode.apiSPA.model;

import java.time.LocalDate;
import java.time.LocalTime;



import jakarta.persistence.*;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Usuario profesional;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @Enumerated(EnumType.STRING)
    private EstadoTurno estado;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    private boolean pagado;
    private boolean pagoWeb;
    private double monto;

    @Column(length = 500)
    private String detalle;

    // Constructor vacío
    public Turno() {
    }

    // Constructor con todos los campos
    public Turno(Integer id, Usuario cliente, Usuario profesional, Servicio servicio, EstadoTurno estado,
         MetodoPago metodoPago, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
         boolean pagado, boolean pagoWeb, double monto, String detalle) {
        this.id = id;
        this.cliente = cliente;
        this.profesional = profesional;
        this.servicio = servicio;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.pagado = pagado;
        this.pagoWeb = pagoWeb;
        this.monto = monto;
        this.detalle = detalle;
    }

    // Getters y setters (solo pongo algunos, podés generar todos en tu IDE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getProfesional() {
        return profesional;
    }

    public void setProfesional(Usuario profesional) {
        this.profesional = profesional;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(com.levitacode.apiSPA.model.EstadoTurno estado2) {
        this.estado = estado2;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public boolean isPagoWeb() {
        return pagoWeb;
    }

    public void setPagoWeb(boolean pagoWeb) {
        this.pagoWeb = pagoWeb;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
