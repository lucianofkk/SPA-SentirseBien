package com.levitacode.apiSPA.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class Jwtutil {

    private final String jwtSecretBase64 = "q3JkZWxsaWNhbnRlLXZlcnRlLWxhLXNlY3JldGEtMTIzNDU2Nzg5MGFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6";

    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretBase64));

    private final long jwtExpirationMs = 86400000; // 24h

    public String generateJwtToken(String username, String role, String nombre, String apellido, String dni) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("nombre", nombre)
                .claim("apellido", apellido)
                .claim("dni", dni)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRoleFromJwtToken(String token) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
        }
        return false;
    }
}
// Esta clase Jwtutil se encarga de generar, validar y extraer información de los tokens JWT.
// Utiliza una clave secreta para firmar los tokens y asegurar su integridad.
// Los métodos permiten generar un token con el nombre de usuario y rol, extraer el nombre de usuario y rol del token,

