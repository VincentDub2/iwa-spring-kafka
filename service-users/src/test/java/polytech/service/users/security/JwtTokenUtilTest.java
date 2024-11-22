package polytech.service.users.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;
    private final Long userId = 123L;
    private final String role = "ROLE_USER";
    private String validToken;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        validToken = jwtTokenUtil.generateAccessToken(userId, role);
    }

    @Test
    void testGenerateAccessToken() {
        assertNotNull(validToken);
        assertTrue(validToken.startsWith("eyJ")); // Vérifie que le jeton commence par les caractères JWT standards
    }

    @Test
    void testGetUserId() {
        Long extractedUserId = jwtTokenUtil.getUserId(validToken);
        assertEquals(userId, extractedUserId);
    }

    @Test
    void testValidateValidToken() {
        assertTrue(jwtTokenUtil.validate(validToken));
    }

    @Test
    void testValidateInvalidToken() {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.INVALID_SIGNATURE";
        assertFalse(jwtTokenUtil.validate(invalidToken));
    }

    @Test
    void testValidateExpiredToken() {
        SecretKey shortLivedSecret = Keys.hmacShaKeyFor("uneCleSecretePlusLongueEtSecurementStockeeIci123!".getBytes());
        JwtTokenUtil shortLivedJwtTokenUtil = new JwtTokenUtil() {
            @Override
            public String generateAccessToken(Long userId, String role) {
                return Jwts.builder()
                        .subject(String.valueOf(userId))
                        .issuer("polytech.service.users")
                        .claim("role", role)
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() - 1000)) // Déjà expiré
                        .signWith(shortLivedSecret)
                        .compact();
            }
        };

        String expiredToken = shortLivedJwtTokenUtil.generateAccessToken(userId, role);

        assertFalse(jwtTokenUtil.validate(expiredToken));
    }

    @Test
    void testExtractRoleFromToken() {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor("uneCleSecretePlusLongueEtSecurementStockeeIci123!".getBytes()))
                .build()
                .parseSignedClaims(validToken).getPayload();

        String extractedRole = claims.get("role", String.class);
        assertEquals(role, extractedRole);
    }
}
