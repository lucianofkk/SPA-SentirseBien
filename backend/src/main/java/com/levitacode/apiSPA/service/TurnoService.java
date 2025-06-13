package com.levitacode.apiSPA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levitacode.apiSPA.Dto.TurnoDTO;
import com.levitacode.apiSPA.exceptions.TurnoNotFoundException;
import com.levitacode.apiSPA.model.EstadoTurno;
import com.levitacode.apiSPA.model.MetodoPago;
import com.levitacode.apiSPA.model.Turno;
import com.levitacode.apiSPA.model.Usuario;
import com.levitacode.apiSPA.model.Servicio;
import com.levitacode.apiSPA.repository.TurnoRepository;
import com.levitacode.apiSPA.repository.UsuarioRepository;
import com.levitacode.apiSPA.repository.ServicioRepository;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Turno> obtenerTodos() {
        return turnoRepository.findAll();
    }

    public Turno obtenerPorId(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoNotFoundException("Turno no encontrado con ID: " + id));
    }

    public Turno crear(Turno turno) {
        return turnoRepository.save(turno);
    }

    public Turno crearDesdeDTO(TurnoDTO dto) {
    Turno turno = new Turno();

    // Setear fecha y hora
    turno.setFecha(dto.getFecha());
    turno.setHoraInicio(dto.getHoraInicio());
    turno.setHoraFin(dto.getHoraFin());

    // Buscar cliente y setear
    Usuario cliente = usuarioRepository.findById(dto.getClienteId().longValue())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));
    turno.setCliente(cliente);

    // Buscar profesional y setear
    Usuario profesional = usuarioRepository.findById(dto.getProfesionalId().longValue())
            .orElseThrow(() -> new RuntimeException("Profesional no encontrado con ID: " + dto.getProfesionalId()));
    turno.setProfesional(profesional);

    // Buscar servicio y setear
    Servicio servicio = servicioRepository.findById(dto.getServicioId().longValue())
            .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + dto.getServicioId()));
    turno.setServicio(servicio);

    // Convertir Strings a enums
    try {
        EstadoTurno estado = EstadoTurno.valueOf(dto.getEstado());
        turno.setEstado(estado);
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Estado inválido: " + dto.getEstado());
    }

    try {
        MetodoPago metodoPago = MetodoPago.valueOf(dto.getMetodoPago());
        turno.setMetodoPago(metodoPago);
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Método de pago inválido: " + dto.getMetodoPago());
    }

    // Otros campos
    turno.setPagado(dto.isPagado());
    turno.setPagoWeb(dto.isPagoWeb());
    turno.setMonto(dto.getMonto());
    turno.setDetalle(dto.getDetalle());

    return turnoRepository.save(turno);
}

    public Turno actualizar(Long id, Turno turnoActualizado) {
        Turno existente = obtenerPorId(id);
        existente.setFecha(turnoActualizado.getFecha());
        existente.setHoraInicio(turnoActualizado.getHoraInicio());
        existente.setHoraFin(turnoActualizado.getHoraFin());
        existente.setEstado(turnoActualizado.getEstado());
        existente.setServicio(turnoActualizado.getServicio());
        existente.setCliente(turnoActualizado.getCliente());
        existente.setProfesional(turnoActualizado.getProfesional());
        existente.setMetodoPago(turnoActualizado.getMetodoPago());
        existente.setPagado(turnoActualizado.isPagado());
        existente.setPagoWeb(turnoActualizado.isPagoWeb());
        existente.setMonto(turnoActualizado.getMonto());
        existente.setDetalle(turnoActualizado.getDetalle());

        return turnoRepository.save(existente);
    }

    public void eliminar(Long id) {
        turnoRepository.deleteById(id);
    }
}
