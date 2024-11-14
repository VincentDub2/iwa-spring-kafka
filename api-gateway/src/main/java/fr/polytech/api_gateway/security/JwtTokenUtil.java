package fr.polytech.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    //TODO: Stocker le secret JWT dans une variable d'environnement
    private final SecretKey jwtSecret = Keys.hmacShaKeyFor("uneCleSecretePlusLongueEtSecurementStockeeIci123!".getBytes()); // Stocker le secret JWT dans une variable d'environnement
    private final String jwtIssuer = "polytech.service.users";


    /**
        * Génère un token JWT pour l'utilisateur spécifié
        @param userId ID de l'utilisateur
        @return Token JWT
     */
    public String generateAccessToken(Long userId) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuer(jwtIssuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 heures
                .signWith(jwtSecret)
                .compact();
    }

    /**
        * Extrait l'ID de l'utilisateur du token JWT
        @param token Token JWT
        @return ID de l'utilisateur
     */
    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token).getPayload();

        return Long.parseLong(claims.getSubject());
    }

    /**
        * Valide le token JWT
        @param token Token JWT
        @return true si le token est valide, sinon false
     */
    public boolean validate(String token) {
        try {
            Jwts.parser().verifyWith(jwtSecret).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
