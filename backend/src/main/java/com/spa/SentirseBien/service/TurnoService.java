package com.spa.SentirseBien.service;

import com.spa.SentirseBien.exception.*;
import com.spa.SentirseBien.model.*;
import com.spa.SentirseBien.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProfesionalRepository profesionalRepository;
    private final ServicioRepository servicioRepository;

    // Crear turno (valida cliente, profesional, servicio y horario)
    public Turno crearTurno(Turno turno) {
        // 1. Validar que existan cliente, profesional y servicio
        Usuario cliente = usuarioRepository.findById(turno.getCliente().getId())
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado"));
        Profesional profesional = profesionalRepository.findById(turno.getProfesional().getId())
                .orElseThrow(() -> new ProfesionalNotFoundException("Profesional no encontrado"));
        Servicio servicio = servicioRepository.findById(turno.getServicio().getId())
                .orElseThrow(() -> new ServicioNotFoundException("Servicio no encontrado"));

        // 2. Validar disponibilidad del profesional
        LocalDateTime inicio = turno.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(servicio.getDuracion());
        List<Turno> turnosSolapados = turnoRepository.findByProfesionalIdAndFechaHoraBetween(
                profesional.getId(), inicio, fin);
        if (!turnosSolapados.isEmpty()) {
            throw new IllegalArgumentException("El profesional ya tiene un turno en ese horario");
        }

        // 3. Guardar el turno
        turno.setCliente(cliente);
        turno.setProfesional(profesional);
        turno.setServicio(servicio);
        return turnoRepository.save(turno);
    }

    // Listar todos los turnos
    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }

    // Obtener turno por ID
    public Turno obtenerTurnoPorId(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoNotFoundException("Turno no encontrado"));
    }

    // Listar turnos por cliente
    public List<Turno> listarTurnosPorCliente(Long clienteId) {
        return turnoRepository.findByClienteId(clienteId);
    }

    // Eliminar turno
    public void eliminarTurno(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new TurnoNotFoundException("Turno no encontrado");
        }
        turnoRepository.deleteById(id);
    }
}