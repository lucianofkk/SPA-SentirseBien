package com.spa.SentirseBien.service;

import com.spa.SentirseBien.exception.RolNotFoundException;
import com.spa.SentirseBien.model.Rol;
import com.spa.SentirseBien.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    //Crear un nuevo rol (valida nombre único)
    public Rol crearRol(Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new IllegalArgumentException("Ya existe un rol con este nombre");
        }
        return rolRepository.save(rol);
    }

    //Listar todos los roles
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    //Obtener rol por ID (lanza excepción si no existe)
    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));
    }

    //Actualizar rol (valida que exista el ID)
    public Rol actualizarRol(Long id, Rol rolActualizado) {
        return rolRepository.findById(id)
                .map(rol -> {
                    rol.setNombre(rolActualizado.getNombre());
                    return rolRepository.save(rol);
                })
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));
    }

    // Eliminar rol (valida que exista el ID)
    public void eliminarRol(Long id) {
        if (!rolRepository.existsById(id)) {
            throw new RolNotFoundException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }
}