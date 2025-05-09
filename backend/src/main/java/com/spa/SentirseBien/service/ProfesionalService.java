package com.spa.SentirseBien.service;

import com.spa.SentirseBien.exception.EmailAlreadyExistsException;
import com.spa.SentirseBien.exception.ProfesionalNotFoundException;
import com.spa.SentirseBien.model.Profesional;
import com.spa.SentirseBien.repository.ProfesionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;

    //Crear profesional (valida email único)
    public Profesional crearProfesional(Profesional profesional) {
        if (profesionalRepository.existsByEmail(profesional.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }
        return profesionalRepository.save(profesional);
    }

    //Listar todos
    public List<Profesional> listarProfesionales() {
        return profesionalRepository.findAll();
    }

    //Obtener por ID
    public Profesional obtenerProfesionalPorId(Long id) {
        return profesionalRepository.findById(id)
                .orElseThrow(() -> new ProfesionalNotFoundException("Profesional no encontrado"));
    }

    //Eliminar
    public void eliminarProfesional(Long id) {
        if (!profesionalRepository.existsById(id)) {
            throw new ProfesionalNotFoundException("Profesional no encontrado");
        }
        profesionalRepository.deleteById(id);
    }
}