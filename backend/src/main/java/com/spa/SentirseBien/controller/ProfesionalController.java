package com.spa.SentirseBien.controller;

import com.spa.SentirseBien.exception.EmailAlreadyExistsException;
import com.spa.SentirseBien.exception.ProfesionalNotFoundException;
import com.spa.SentirseBien.model.Profesional;
import com.spa.SentirseBien.service.ProfesionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesionales")
@RequiredArgsConstructor
public class ProfesionalController {

    private final ProfesionalService profesionalService;

    // POST: Crear profesional
    @PostMapping
    public ResponseEntity<?> crearProfesional(@RequestBody Profesional profesional) {
        try {
            Profesional nuevo = profesionalService.crearProfesional(profesional);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Profesional>> listarProfesionales() {
        return ResponseEntity.ok(profesionalService.listarProfesionales());
    }

    // GET: Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProfesionalPorId(@PathVariable Long id) {
        try {
            Profesional profesional = profesionalService.obtenerProfesionalPorId(id);
            return ResponseEntity.ok(profesional);
        } catch (ProfesionalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProfesional(@PathVariable Long id) {
        try {
            profesionalService.eliminarProfesional(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (ProfesionalNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}