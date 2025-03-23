package com.project.socialMedia.service;

import com.project.socialMedia.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService socialUserService; // Service for handling users

    // Method to handle user login
    public String login(String email, String password) {
        // Retrieve user by email
        Optional<User> userOptional = socialUserService.getUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                // Successful login
                return "Login Successful";
            } else {
                // Incorrect username or password
                return "Username/Password Incorrect";
            }
        } else {
            // User does not exist
            return "User does not exist";
        }
    }

    // Method to handle user signup
    public String signup(String email, String name, String password) {
        // Check if user already exists
        Optional<User> existingUser = socialUserService.getUserByEmail(email);
        if (existingUser.isPresent()) {
            // Account already exists
            return "Forbidden, Account already exists";
        } else {
            // Create new user
            socialUserService.createUser(email, name, password);
            return "Account Creation Successful";
        }
    }
}
