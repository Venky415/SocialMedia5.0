package com.project.socialMedia.entity;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the user

    private String name; // Name of the user

    private String email; // Email address of the user

    private String password; // Password of the user

    // Constructors
    public User(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User() {
        // Default constructor
    }
}
