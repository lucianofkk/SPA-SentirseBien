package com.spa.SentirseBien.controller;

import com.spa.SentirseBien.exception.ServicioNotFoundException;
import com.spa.SentirseBien.model.Servicio;
import com.spa.SentirseBien.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    // POST: Crear servicio
    @PostMapping
    public ResponseEntity<?> crearServicio(@RequestBody Servicio servicio) {
        try {
            Servicio nuevo = servicioService.crearServicio(servicio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Servicio>> listarServicios() {
        return ResponseEntity.ok(servicioService.listarServicios());
    }

    // GET: Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerServicioPorId(@PathVariable Long id) {
        try {
            Servicio servicio = servicioService.obtenerServicioPorId(id);
            return ResponseEntity.ok(servicio);
        } catch (ServicioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarServicio(
            @PathVariable Long id,
            @RequestBody Servicio servicio) {
        try {
            Servicio actualizado = servicioService.actualizarServicio(id, servicio);
            return ResponseEntity.ok(actualizado);
        } catch (ServicioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long id) {
        try {
            servicioService.eliminarServicio(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (ServicioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}