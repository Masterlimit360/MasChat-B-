package com.postgresql.MasChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.dto.RegisterRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            return ResponseEntity.ok(new ApiResponse("User registered successfully!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse("User deleted"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/by-username/{username}")
    public ResponseEntity<ApiResponse> deleteUserByUsername(@PathVariable String username) {
        boolean deleted = userService.deleteByUsername(username);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse("User deleted"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public class ApiResponse {
        private String message;
        public ApiResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}
