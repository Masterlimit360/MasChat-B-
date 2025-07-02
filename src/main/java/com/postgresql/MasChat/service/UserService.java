package com.postgresql.MasChat.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.postgresql.MasChat.dto.ProfileUpdateRequest;
import com.postgresql.MasChat.dto.RegisterRequest;
import com.postgresql.MasChat.dto.UserDetailsDTO;
import com.postgresql.MasChat.exception.ResourceNotFoundException;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.model.UserDetails;
import com.postgresql.MasChat.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public User updateProfile(Long userId, ProfileUpdateRequest request) {
        User user = this.findById(userId);
        
        if (request.getBio() != null) user.setBio(request.getBio());
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        
        if (request.getDetails() != null) {
            if (user.getDetails() == null) {
                user.setDetails(new UserDetails());
            }
            UserDetailsDTO detailsDto = request.getDetails();
            if (detailsDto.getProfileType() != null) user.getDetails().setProfileType(detailsDto.getProfileType());
            if (detailsDto.getWorksAt1() != null) user.getDetails().setWorksAt1(detailsDto.getWorksAt1());
            if (detailsDto.getWorksAt2() != null) user.getDetails().setWorksAt2(detailsDto.getWorksAt2());
            if (detailsDto.getStudiedAt() != null) user.getDetails().setStudiedAt(detailsDto.getStudiedAt());
            if (detailsDto.getWentTo() != null) user.getDetails().setWentTo(detailsDto.getWentTo());
            if (detailsDto.getCurrentCity() != null) user.getDetails().setCurrentCity(detailsDto.getCurrentCity());
            if (detailsDto.getHometown() != null) user.getDetails().setHometown(detailsDto.getHometown());
            if (detailsDto.getRelationshipStatus() != null) user.getDetails().setRelationshipStatus(detailsDto.getRelationshipStatus());
            if (detailsDto.getShowAvatar() != null) user.getDetails().setShowAvatar(detailsDto.getShowAvatar());
            if (detailsDto.getAvatar() != null) user.getDetails().setAvatar(detailsDto.getAvatar());
        }
        
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public User updateProfilePicture(Long userId, String imageUrl) {
        User user = this.findById(userId);
        user.setProfilePicture(imageUrl);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public User updateCoverPhoto(Long userId, String imageUrl) {
        User user = this.findById(userId);
        user.setCoverPhoto(imageUrl);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public User updateAvatar(Long userId, String imageUrl, boolean showAvatar) {
        User user = this.findById(userId);
        if (user.getDetails() == null) {
            user.setDetails(new UserDetails());
        }
        user.getDetails().setAvatar(imageUrl);
        user.getDetails().setShowAvatar(showAvatar);
        user.setUpdatedAt(LocalDateTime.now());
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
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }
}