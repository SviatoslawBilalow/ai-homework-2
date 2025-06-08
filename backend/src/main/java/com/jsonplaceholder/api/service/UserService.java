package com.jsonplaceholder.api.service;

import com.jsonplaceholder.api.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
} 