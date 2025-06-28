package com.postgresql.MasChat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.dto.RegisterRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.UserRepository;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername().trim()); // <-- Place it here
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encode here!
        // set other fields as needed
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean deleteByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }

    public boolean deleteById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }
}
