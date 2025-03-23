package com.project.socialMedia.controller;

import com.project.socialMedia.entity.User;
import com.project.socialMedia.response.UserResponse;
import com.project.socialMedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService socialUserService; // Service for handling users

    // Endpoint to get all users
    @GetMapping("/api/users")
    public ResponseEntity<?> getAllUsers() {
        // Return all users
        return ResponseEntity.ok(socialUserService.getAllUsers());
    }

    // Endpoint to get user details by ID
    @GetMapping("/api/user")
    public ResponseEntity<?> getUserDetail(@RequestParam("userID") Long userId) {
        // Retrieve user by ID
        User user = socialUserService.getUserById(userId);
        if (user != null) {
            // Map user entity to response DTO
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getId());
            userResponse.setName(user.getName());
            userResponse.setEmail(user.getEmail());
            return ResponseEntity.ok(userResponse);
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    }
}
