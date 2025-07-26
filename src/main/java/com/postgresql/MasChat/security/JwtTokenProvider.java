package com.postgresql.MasChat.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;


@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        System.out.println("=== Generating JWT Token ===");
        System.out.println("Username: " + username);
        System.out.println("Secret: " + secret.substring(0, Math.min(10, secret.length())) + "...");
        System.out.println("Expiration: " + expiration + "ms");
        
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            System.out.println("Error extracting username from token: " + e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("Validating token with secret: " + secret.substring(0, Math.min(10, secret.length())) + "...");
            System.out.println("Token to validate: " + token.substring(0, Math.min(20, token.length())) + "...");
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            System.out.println("Token validation successful");
            return true;
        } catch (JwtException e) {
            System.out.println("JWT validation failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Unexpected error during token validation: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
