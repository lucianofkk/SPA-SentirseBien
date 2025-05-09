package com.spa.SentirseBien.repository;

import com.spa.SentirseBien.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombre(String nombre);
}