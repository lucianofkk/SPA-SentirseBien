package com.levitacode.apiSPA.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levitacode.apiSPA.Dto.RegistroDTO;
import com.levitacode.apiSPA.model.Rol;
import com.levitacode.apiSPA.model.Usuario;
import com.levitacode.apiSPA.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario registrarUsuario(RegistroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setDni(dto.getDni());
        usuario.setActivo(true);
        usuario.setRol(dto.getRol() != null ? dto.getRol() : Rol.CLIENTE); 

        return usuarioRepository.save(usuario);
    }
}
