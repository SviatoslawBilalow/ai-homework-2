package com.jsonplaceholder.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonplaceholder.api.model.User;
import com.jsonplaceholder.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = new User();
        testUser.setName("Integration Test User");
        testUser.setUsername("integrationuser");
        testUser.setEmail("integration@example.com");
        testUser.setPassword("password");
        
        testUser = userRepository.save(testUser);
    }

    @Test
    @WithMockUser
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("Integration Test User")))
                .andExpect(jsonPath("$[0].username", is("integrationuser")))
                .andExpect(jsonPath("$[0].email", is("integration@example.com")));
    }

    @Test
    @WithMockUser
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users/" + testUser.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testUser.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Integration Test User")))
                .andExpect(jsonPath("$.username", is("integrationuser")))
                .andExpect(jsonPath("$.email", is("integration@example.com")));
    }

    @Test
    @WithMockUser
    void getUserById_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/users/999")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createUser_ShouldCreateAndReturnUser() throws Exception {
        // Arrange
        User newUser = new User();
        newUser.setName("New User");
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");
        newUser.setPassword("password");

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("New User")))
                .andExpect(jsonPath("$.username", is("newuser")))
                .andExpect(jsonPath("$.email", is("new@example.com")));
    }

    @Test
    @WithMockUser
    void updateUser_WhenUserExists_ShouldUpdateAndReturnUser() throws Exception {
        // Arrange
        testUser.setName("Updated User");
        testUser.setEmail("updated@example.com");

        // Act & Assert
        mockMvc.perform(put("/api/users/" + testUser.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testUser.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Updated User")))
                .andExpect(jsonPath("$.username", is("integrationuser")))
                .andExpect(jsonPath("$.email", is("updated@example.com")));
    }

    @Test
    @WithMockUser
    void updateUser_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        User nonExistentUser = new User();
        nonExistentUser.setId(999L);
        nonExistentUser.setName("Non-existent User");
        nonExistentUser.setUsername("nonexistentuser");
        nonExistentUser.setEmail("nonexistent@example.com");
        nonExistentUser.setPassword("password");

        // Act & Assert
        mockMvc.perform(put("/api/users/999")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistentUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteUser_WhenUserExists_ShouldDeleteUser() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/users/" + testUser.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        // Verify user is deleted
        mockMvc.perform(get("/api/users/" + testUser.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }
}