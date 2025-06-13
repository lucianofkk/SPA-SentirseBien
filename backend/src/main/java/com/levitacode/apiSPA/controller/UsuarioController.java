package com.levitacode.apiSPA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;  // IMPORT
import org.springframework.web.bind.annotation.DeleteMapping;  // IMPORT
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levitacode.apiSPA.Dto.UsuarioPerfilDTO;
import com.levitacode.apiSPA.model.Usuario;
import com.levitacode.apiSPA.service.UsuarioService;  // IMPORT DTO

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("El email es requerido");
            }
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("La contraseña es requerida");
            }
            if (usuarioService.existeUsuarioConEmail(usuario.getEmail())) {
                return ResponseEntity.badRequest().body("El email ya está registrado");
            }
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar usuario: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    // --- NUEVO ENDPOINT PARA OBTENER EL PERFIL DEL USUARIO LOGUEADO ---

@GetMapping("/perfil/{email}")
public ResponseEntity<?> obtenerPerfilUsuario(@PathVariable String email) {
    return usuarioService.obtenerUsuarioPorEmail(email)
        .map(usuario -> {
            UsuarioPerfilDTO perfil = new UsuarioPerfilDTO(
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getDni()
            );
            return ResponseEntity.ok(perfil);
        })
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
}




}
