package com.cinemaapp.cinema.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long jwtExpirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long jwtExpirationMs) {

        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    // Generate JWT token
    public String generateToken(String email) {

        Date currentDate = new Date();

        Date expirationDate = new Date(
                currentDate.getTime() + jwtExpirationMs
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(signingKey)
                .compact();
    }

    // Extract email from JWT token
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract all claims from JWT token
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validate JWT token
    public boolean isTokenValid(String token, String email) {

        try {
            String tokenEmail = extractEmail(token);
            return tokenEmail.equals(email);

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}