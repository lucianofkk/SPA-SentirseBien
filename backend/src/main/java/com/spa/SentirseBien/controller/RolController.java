package com.spa.SentirseBien.controller;

import com.spa.SentirseBien.exception.RolNotFoundException;
import com.spa.SentirseBien.model.Rol;
import com.spa.SentirseBien.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    // POST: Crear rol
    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody Rol rol) {
        try {
            Rol nuevo = rolService.crearRol(rol);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // HTTP 400 si el nombre ya existe
        }
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }

    // GET: Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerRol(@PathVariable Long id) {
        try {
            Rol rol = rolService.obtenerRolPorId(id);
            return ResponseEntity.ok(rol);
        } catch (RolNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // HTTP 404
        }
    }

    // PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody Rol rol) {
        try {
            Rol actualizado = rolService.actualizarRol(id, rol);
            return ResponseEntity.ok(actualizado);
        } catch (RolNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Long id) {
        try {
            rolService.eliminarRol(id);
            return ResponseEntity.noContent().build(); // HTTP 204 (Éxito sin contenido)
        } catch (RolNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}