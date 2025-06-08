package com.jsonplaceholder.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String username;
    private String email;
    private String password;
    
    @Embedded
    private Address address;
    
    private String phone;
    private String website;
    
    @Embedded
    private Company company;
} 