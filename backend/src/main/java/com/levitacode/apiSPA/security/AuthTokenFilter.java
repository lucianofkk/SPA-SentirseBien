package com.levitacode.apiSPA.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private Jwtutil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response, 
                                FilterChain filterChain) throws ServletException, IOException {
    String path = request.getServletPath();

    // Excluir solo los endpoints públicos explícitamente
    if (path.equals("/api/auth/login") || path.equals("/api/auth/registro")) {
        filterChain.doFilter(request, response);
        return;
    }

    try {
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
            String email = jwtUtil.getUserNameFromJwtToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
            } catch (Exception e) {
                // Opcional: loguear el error para depuración
                e.printStackTrace();
            }

    filterChain.doFilter(request, response);
}

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
