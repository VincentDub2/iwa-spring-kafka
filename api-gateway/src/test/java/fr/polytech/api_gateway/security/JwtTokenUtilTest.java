package fr.polytech.api_gateway.security;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
    }

    @Test
    void testGenerateAccessToken() {
        Long userId = 123L;

        String token = jwtTokenUtil.generateAccessToken(userId);

        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Test
    void testGetUserIdFromToken() {
        Long userId = 123L;
        String token = jwtTokenUtil.generateAccessToken(userId);

        Long extractedUserId = jwtTokenUtil.getUserId(token);

        assertNotNull(extractedUserId, "Extracted userId should not be null");
        assertEquals(userId, extractedUserId, "Extracted userId should match the original userId");
    }

    @Test
    void testValidateToken() {
        Long userId = 123L;
        String token = jwtTokenUtil.generateAccessToken(userId);

        boolean isValid = jwtTokenUtil.validate(token);

        assertTrue(isValid, "Token should be valid");
    }

    @Test
    void testValidateInvalidToken() {
        String invalidToken = "invalid.token.here";

        boolean isValid = jwtTokenUtil.validate(invalidToken);

        assertFalse(isValid, "Invalid token should not be valid");
    }

    @Test
    void testExpiredToken() {
        SecretKey secretKey = Keys.hmacShaKeyFor("uneCleSecretePlusLongueEtSecurementStockeeIci123!".getBytes());
        String expiredToken = io.jsonwebtoken.Jwts.builder()
                .subject("123")
                .issuer("polytech.service.users")
                .issuedAt(new Date(System.currentTimeMillis() - 86400000)) // Issued 24 hours ago
                .expiration(new Date(System.currentTimeMillis() - 3600000)) // Expired 1 hour ago
                .signWith(secretKey)
                .compact();

        boolean isValid = jwtTokenUtil.validate(expiredToken);

        assertFalse(isValid, "Expired token should not be valid");
    }
}
