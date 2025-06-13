package com.levitacode.apiSPA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levitacode.apiSPA.model.Servicio;
import com.levitacode.apiSPA.service.ServicioService;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Servicio obtenerServicio(@PathVariable Long id) {
        return servicioService.obtenerPorId(id);
    }

    @PostMapping
    public Servicio crearServicio(@RequestBody Servicio servicio) {
        return servicioService.crear(servicio);
    }

    @PutMapping("/{id}")
    public Servicio actualizarServicio(@PathVariable Long id, @RequestBody Servicio servicio) {
        return servicioService.actualizar(id, servicio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Long id) {
    servicioService.eliminar(id);
    return ResponseEntity.noContent().build(); // HTTP 204
}
}
