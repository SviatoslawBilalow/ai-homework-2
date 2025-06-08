package com.jsonplaceholder.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonplaceholder.api.model.TestLoginRequest;
import com.jsonplaceholder.api.model.User;
import com.jsonplaceholder.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private TestLoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Create a test user with encoded password
        testUser = new User();
        testUser.setName("Auth Test User");
        testUser.setUsername("authuser");
        testUser.setEmail("auth@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));

        testUser = userRepository.save(testUser);

        // Create login request
        loginRequest = new TestLoginRequest();
        loginRequest.setUsername("authuser");
        loginRequest.setPassword("password");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.token", not(emptyString())));
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        // Arrange
        loginRequest.setPassword("wrongpassword");

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is4xxClientError()); // Accept any 4xx error code
    }

    @Test
    void register_WithNewUser_ShouldCreateAndReturnUser() throws Exception {
        // Arrange
        User newUser = new User();
        newUser.setName("New Auth User");
        newUser.setUsername("newauthuser");
        newUser.setEmail("newauth@example.com");
        newUser.setPassword("password");

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("New Auth User")))
                .andExpect(jsonPath("$.username", is("newauthuser")))
                .andExpect(jsonPath("$.email", is("newauth@example.com")));
    }

    @Test
    void register_WithExistingUsername_ShouldReturnBadRequest() throws Exception {
        // Arrange
        User userWithExistingUsername = new User();
        userWithExistingUsername.setName("Duplicate Username");
        userWithExistingUsername.setUsername("authuser"); // Same as testUser
        userWithExistingUsername.setEmail("different@example.com");
        userWithExistingUsername.setPassword("password");

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWithExistingUsername)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username is already taken!"));
    }

    @Test
    void register_WithExistingEmail_ShouldReturnBadRequest() throws Exception {
        // Arrange
        User userWithExistingEmail = new User();
        userWithExistingEmail.setName("Duplicate Email");
        userWithExistingEmail.setUsername("differentuser");
        userWithExistingEmail.setEmail("auth@example.com"); // Same as testUser
        userWithExistingEmail.setPassword("password");

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWithExistingEmail)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email is already in use!"));
    }
}
