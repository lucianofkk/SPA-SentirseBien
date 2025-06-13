package com.levitacode.apiSPA.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.levitacode.apiSPA.model.Usuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    String authority = "ROLE_" + usuario.getRol().name();
    System.out.println(">> Authority asignada: " + authority); // LOG
    return List.of(new SimpleGrantedAuthority(authority));
}
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return usuario.isActivo();
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.isActivo();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.isActivo();
    }

    @Override
    public boolean isEnabled() {
        return usuario.isActivo();
    }
} 
// Esta clase implementa UserDetails y proporciona la información del usuario
// que Spring Security necesita para autenticar y autorizar al usuario.
// Incluye el rol del usuario y verifica si la cuenta está activa.
// Además, se utiliza en el servicio UserDetailsServiceImpl para cargar los detalles del usuario por su email.
// La anotación @AllArgsConstructor de Lombok genera un constructor con todos los campos.
// Esto permite crear una instancia de UserDetailsImpl pasando un objeto Usuario.
// La clase UserDetailsImpl implementa la interfaz UserDetails de Spring Security,

