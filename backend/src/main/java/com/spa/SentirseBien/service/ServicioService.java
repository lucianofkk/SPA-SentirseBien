package com.spa.SentirseBien.service;

import com.spa.SentirseBien.exception.ServicioNotFoundException;
import com.spa.SentirseBien.model.Servicio;
import com.spa.SentirseBien.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    // Crear servicio con validaciones
    public Servicio crearServicio(Servicio servicio) {
        if (servicio.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (servicioRepository.existsByNombre(servicio.getNombre())) {
            throw new IllegalArgumentException("Ya existe un servicio con ese nombre");
        }
        return servicioRepository.save(servicio);
    }

    // Listar todos los servicios
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    // Filtrar por tipo (masaje, facial, etc.)
    public List<Servicio> listarServiciosPorTipo(String tipo) {
        return servicioRepository.findByTipo(tipo);
    }

    // Buscar por ID
    public Servicio obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ServicioNotFoundException("Servicio no encontrado"));
    }

    // Actualizar servicio (con validaciones)
    public Servicio actualizarServicio(Long id, Servicio servicioActualizado) {
        if (servicioActualizado.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        
        return servicioRepository.findById(id)
                .map(servicio -> {
                    // Verifica si el nombre nuevo ya existe (y no es el mismo servicio)
                    if (!servicio.getNombre().equals(servicioActualizado.getNombre()) 
                        && servicioRepository.existsByNombre(servicioActualizado.getNombre())) {
                        throw new IllegalArgumentException("Ya existe un servicio con ese nombre");
                    }
                    servicio.setNombre(servicioActualizado.getNombre());
                    servicio.setTipo(servicioActualizado.getTipo());
                    servicio.setDescripcion(servicioActualizado.getDescripcion());
                    servicio.setPrecio(servicioActualizado.getPrecio());
                    return servicioRepository.save(servicio);
                })
                .orElseThrow(() -> new ServicioNotFoundException("Servicio no encontrado"));
    }

    // Eliminar servicio
    public void eliminarServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new ServicioNotFoundException("Servicio no encontrado");
        }
        servicioRepository.deleteById(id);
    }
}