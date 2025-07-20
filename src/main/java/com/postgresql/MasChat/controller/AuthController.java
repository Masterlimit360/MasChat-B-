package com.postgresql.MasChat.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.dto.ForgotPasswordRequest;
import com.postgresql.MasChat.dto.LoginRequest;
import com.postgresql.MasChat.dto.ProfileUpdateRequest;
import com.postgresql.MasChat.dto.RegisterRequest;
import com.postgresql.MasChat.dto.ResetPasswordRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.security.JwtTokenProvider;
import com.postgresql.MasChat.service.PasswordResetService;
import com.postgresql.MasChat.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private UserService userService;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private PasswordResetService passwordResetService;

    @PutMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateRequest request
    ) {
        User updatedUser = userService.updateProfile(id, request);
        return ResponseEntity.ok(updatedUser);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User registeredUser = userService.register(request);
            String token = jwtTokenProvider.generateToken(registeredUser.getUsername());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully!");
            response.put("token", token);
            response.put("username", registeredUser.getUsername());
            response.put("userId", registeredUser.getId());
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtTokenProvider.generateToken(user.getUsername());

            // You can create a DTO for user if you want to avoid exposing sensitive fields
            Map<String, Object> userObj = new HashMap<>();
            userObj.put("username", user.getUsername());
            userObj.put("userId", user.getId());
            userObj.put("profilePicture", user.getProfilePicture());
            userObj.put("fullName", user.getFullName());
            userObj.put("email", user.getEmail());
            // add more fields as needed

            return ResponseEntity.ok(Map.of(
                "message", "Login successful!",
                "token", token,
                "user", userObj
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            passwordResetService.forgotPassword(request);
            return ResponseEntity.ok(Map.of("message", "If an account with that email exists, a password reset link has been sent."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            passwordResetService.resetPassword(request);
            return ResponseEntity.ok(Map.of("message", "Password has been reset successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("Backend connection successful");
    }
}