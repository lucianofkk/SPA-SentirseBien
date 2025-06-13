package com.levitacode.apiSPA.service;

import com.levitacode.apiSPA.exceptions.ServicioNotFoundException;
import com.levitacode.apiSPA.model.Servicio;
import com.levitacode.apiSPA.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ServicioNotFoundException("Servicio no encontrado con id: " + id));
    }

    public Servicio crear(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Long id, Servicio nuevo) {
        Servicio existente = obtenerPorId(id);
        existente.setNombre(nuevo.getNombre());
        existente.setTipo(nuevo.getTipo());
        existente.setDescripcion(nuevo.getDescripcion());
        existente.setPrecio(nuevo.getPrecio());
        return servicioRepository.save(existente);
    }

    public void eliminar(Long id) {
        Servicio existente = obtenerPorId(id);
        servicioRepository.delete(existente);
    }
}
