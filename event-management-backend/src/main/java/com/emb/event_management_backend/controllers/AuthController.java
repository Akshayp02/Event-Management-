package com.emb.event_management_backend.controllers;

import com.emb.event_management_backend.Entity.User;
import com.emb.event_management_backend.models.JwtResponse;
import com.emb.event_management_backend.services.UserService;
import com.emb.event_management_backend.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    // Register a new user

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Check if the user already exists
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists with this email.");
        }

        // Save the new user
        User newUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User validUser = userService.authenticate(user.getEmail(), user.getPassword());
        if (validUser != null) {
            String token = jwtUtil.generateToken(validUser.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
