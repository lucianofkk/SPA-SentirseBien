package com.levitacode.apiSPA.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.levitacode.apiSPA.exceptions.EmailAlreadyExistsException;
import com.levitacode.apiSPA.model.Usuario;
import com.levitacode.apiSPA.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Crear usuario con validación de email único
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar por ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    // Actualizar usuario
    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setEmail(usuarioActualizado.getEmail());
                    usuario.setTelefono(usuarioActualizado.getTelefono());
                    usuario.setRol(usuarioActualizado.getRol());
                    return usuarioRepository.save(usuario);
                });
    }

    // Eliminar usuario
    public boolean eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar por email (útil para login)
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean existeUsuarioConEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existeUsuarioConEmail'");
    }
}