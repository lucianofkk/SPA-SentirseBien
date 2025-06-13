package com.levitacode.apiSPA.repository;

import com.levitacode.apiSPA.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    boolean existsByNombre(String nombre); // Verifica si ya existe un servicio con ese nombre
    List<Servicio> findByTipo(String tipo); // Filtra servicios por tipo (masaje, facial, etc.)
}