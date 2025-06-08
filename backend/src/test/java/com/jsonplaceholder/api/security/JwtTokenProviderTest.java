package com.jsonplaceholder.api.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtTokenProviderTest {

    private JwtTokenProvider tokenProvider;
    private UserDetails userDetails;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "jwtSecret", "test-secret-key-for-jwt-token-generation-and-validation");
        ReflectionTestUtils.setField(tokenProvider, "jwtExpiration", 3600000L); // 1 hour

        userDetails = new User("testuser", "password", new ArrayList<>());
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        // Act
        String token = tokenProvider.generateToken(authentication);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void getUsernameFromToken_ShouldReturnCorrectUsername() {
        // Arrange
        String token = tokenProvider.generateToken(authentication);

        // Act
        String username = tokenProvider.getUsernameFromToken(token);

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        // Arrange
        String token = tokenProvider.generateToken(authentication);

        // Act
        boolean isValid = tokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        // Arrange
        String invalidToken = "invalid.token.string";

        // Act
        boolean isValid = tokenProvider.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_WithExpiredToken_ShouldReturnFalse() throws Exception {
        // Arrange
        // Set a very short expiration time
        ReflectionTestUtils.setField(tokenProvider, "jwtExpiration", 1L); // 1 millisecond
        String token = tokenProvider.generateToken(authentication);
        
        // Wait for token to expire
        Thread.sleep(10);

        // Act
        boolean isValid = tokenProvider.validateToken(token);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_WithModifiedToken_ShouldReturnFalse() {
        // Arrange
        String token = tokenProvider.generateToken(authentication);
        String modifiedToken = token + "modified";

        // Act
        boolean isValid = tokenProvider.validateToken(modifiedToken);

        // Assert
        assertFalse(isValid);
    }
}