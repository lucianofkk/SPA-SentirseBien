package com.spa.SentirseBien.repository;

import com.spa.SentirseBien.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos CRUD básicos ya proporcionados por JpaRepository

    // Verifica si ya existe un usuario con ese nombre de usuario
    boolean existsByUsername(String username);

    // Verifica si ya existe un usuario con ese número de documento
    boolean existsByNumeroDocumento(String numeroDocumento);

    // Verifica si ya existe un usuario con ese número de teléfono
    boolean existsByTelefono(String telefono);
    // Métodos personalizados útiles
    boolean existsByEmail(String email);


    // Busca un usuario por su email (útil para login)
    //Optional<Usuario> findByEmail(String email);
}