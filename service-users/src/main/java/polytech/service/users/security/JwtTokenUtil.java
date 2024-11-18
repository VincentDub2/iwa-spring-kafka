package polytech.service.users.security;

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

    private final SecretKey jwtSecret = Keys.hmacShaKeyFor("uneCleSecretePlusLongueEtSecurementStockeeIci123!".getBytes()); // Stocker le secret JWT dans une variable d'environnement
    private final String jwtIssuer = "polytech.service.users";


    /**
     * Generates a JWT token for the specified user
     * @param userId User ID
     * @param role User role
     * @return Token JWT
     */
    public String generateAccessToken(Long userId, String role) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuer(jwtIssuer)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 heures
                .signWith(jwtSecret)
                .compact();
    }

    /**
     * Extracts the user ID from the JWT token
     * @param token JWT token
     * @return User ID
     */
    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token).getPayload();

        return Long.parseLong(claims.getSubject());
    }


    /**
     * Extracts the user role from the JWT token
     * @param token JWT token
     * @return User role
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
