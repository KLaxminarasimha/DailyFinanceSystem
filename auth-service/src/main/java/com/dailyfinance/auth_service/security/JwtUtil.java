package com.dailyfinance.auth_service.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkey";

    // GENERATE TOKEN
    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    // VALIDATE TOKEN
    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // EXTRACT EMAIL
    public String extractEmail(String token) {
        return validateToken(token).getSubject();
    }

    // EXTRACT USER ID
    public Long extractUserId(String token) {
        return validateToken(token).get("userId", Long.class);
    }

    // EXTRACT ROLE
    public String extractRole(String token) {
        return validateToken(token).get("role", String.class);
    }
}