package com.spa.SentirseBien.controller;

import com.spa.SentirseBien.exception.*;
import com.spa.SentirseBien.model.Turno;
import com.spa.SentirseBien.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TurnoController {

    private final TurnoService turnoService;

    // POST: Crear turno
    @PostMapping
    public ResponseEntity<?> crearTurno(@RequestBody Turno turno) {
        try {
            Turno nuevo = turnoService.crearTurno(turno);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (UsuarioNotFoundException | ProfesionalNotFoundException | ServicioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // HTTP 404
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // HTTP 400 (horario ocupado)
        }
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos() {
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    // GET: Listar por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Turno>> listarTurnosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(turnoService.listarTurnosPorCliente(clienteId));
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) {
        try {
            turnoService.eliminarTurno(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (TurnoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}