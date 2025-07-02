package com.postgresql.MasChat.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.postgresql.MasChat.dto.ProfileUpdateRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
    User user = userService.findById(userId); // No need for orElseThrow here
    return ResponseEntity.ok(user);
}   
    @GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
    User user = userService.findById(id);
    return ResponseEntity.ok(user);
}


    @PutMapping("/{userId}/profile")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateRequest request
    ) {
        User updatedUser = userService.updateProfile(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{userId}/profile/picture")
    public ResponseEntity<String> updateProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String imageUrl = saveImage(file, "profile");
            User user = userService.updateProfilePicture(userId, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        }
    }

    @PostMapping("/{userId}/cover/photo")
    public ResponseEntity<String> updateCoverPhoto(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String imageUrl = saveImage(file, "cover");
            User user = userService.updateCoverPhoto(userId, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        }
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<String> updateAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false, defaultValue = "false") boolean showAvatar
    ) {
        try {
            String imageUrl = saveImage(file, "avatar");
            User user = userService.updateAvatar(userId, imageUrl, showAvatar);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        }
    }

    @PostMapping("/{userId}/avatar/picture")
    public ResponseEntity<String> updateAvatarPicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false, defaultValue = "false") boolean showAvatar
    ) {
        try {
            String imageUrl = saveImage(file, "avatar");
            userService.updateAvatar(userId, imageUrl, showAvatar);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload avatar image");
        }
    }

    @PostMapping("/{userId}/cover/picture")
    public ResponseEntity<String> updateCoverPicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String imageUrl = saveImage(file, "cover");
            userService.updateCoverPhoto(userId, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload cover image");
        }
    }

    private String saveImage(MultipartFile file, String type) throws IOException {
        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));
        
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = type + "_" + UUID.randomUUID() + fileExtension;
        
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());
        
        // Return the full URL for the image
        return "http://10.132.74.85:8080/uploads/" + fileName;
    }
}