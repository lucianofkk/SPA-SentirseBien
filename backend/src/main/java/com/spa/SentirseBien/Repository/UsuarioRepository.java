package com.spa.SentirseBien.repository;

import com.spa.SentirseBien.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositroy extends JpaRepository<Cliente, Long> {
 // Métodos personalizados útiles
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}