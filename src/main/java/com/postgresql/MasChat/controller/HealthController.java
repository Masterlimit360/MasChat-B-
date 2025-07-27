package com.postgresql.MasChat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    
    @GetMapping("/health")
    public String health() {
        return "MasChat Backend is running!";
    }
    
    @GetMapping("/")
    public String root() {
        return "Welcome to MasChat Backend API!";
    }
} 