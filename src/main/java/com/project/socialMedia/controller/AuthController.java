package com.project.socialMedia.controller;

import com.project.socialMedia.entity.User;
import com.project.socialMedia.request.UserLoginRequest;
import com.project.socialMedia.request.UserSignupRequest;
import com.project.socialMedia.service.AuthService;
import com.project.socialMedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Prefix for authentication endpoints
public class AuthController {

    private final UserService userService; // Using constructor injection for better testability
    private final AuthService authService; // Using constructor injection for better testability

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        // Retrieve user by email
        User user = userService.getUserByEmail(request.getEmail());
        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(request.getPassword())) {
            // Successful login
            return ResponseEntity.ok("Login Successful");
        } else {
            // Unauthorized access
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // Endpoint for user signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest request) {
        // Check if user already exists
        if (userService.userExists(request.getEmail())) {
            // User already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } else {
            // Create new user
            userService.createUser(request.getEmail(), request.getName(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
        }
    }
}
