package com.postgresql.MasChat.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.postgresql.MasChat.config.AppConfig;
import com.postgresql.MasChat.dto.ProfileUpdateRequest;
import com.postgresql.MasChat.dto.UserDTO;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.model.UserProfile;
import com.postgresql.MasChat.repository.UserRepository;
import com.postgresql.MasChat.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AppConfig appConfig;

    

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

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        List<UserDTO> dtos = users.stream().map(UserDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<User> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateRequest request
    ) {
        User updated = userService.updateProfile(userId, request);
        return ResponseEntity.ok(updated);
    }
    

    @PostMapping("/{userId}/profile/picture")
    public ResponseEntity<String> updateProfilePicture(
            @PathVariable Long userId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }
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
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }
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
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(required = false, defaultValue = "false") boolean showAvatar
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }
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
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(required = false, defaultValue = "false") boolean showAvatar
    ) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }
            String imageUrl = saveImage(file, "avatar");
            User user = userService.updateAvatar(userId, imageUrl, showAvatar);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
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
        
        // Return the full URL for the image using centralized configuration
        return appConfig.getUploadUrl(fileName);
    }

    @PutMapping("/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
    return userRepository.findById(id).map(user -> {
        user.setUsername(updatedUser.getUsername());
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setProfilePicture(updatedUser.getProfilePicture());
        user.setCoverPhoto(updatedUser.getCoverPhoto());
        user.setBio(updatedUser.getBio());

        // ✅ Update details if included in request body
        if (updatedUser.getDetails() != null) {
            if (user.getDetails() == null) {
                user.setDetails(updatedUser.getDetails());
            } else {
                UserProfile details = user.getDetails();
                details.setProfileType(updatedUser.getDetails().getProfileType());
                details.setWorksAt1(updatedUser.getDetails().getWorksAt1());
                details.setWorksAt2(updatedUser.getDetails().getWorksAt2());
                details.setStudiedAt(updatedUser.getDetails().getStudiedAt());
                details.setWentTo(updatedUser.getDetails().getWentTo());
                details.setCurrentCity(updatedUser.getDetails().getCurrentCity());
                details.setHometown(updatedUser.getDetails().getHometown());
                details.setRelationshipStatus(updatedUser.getDetails().getRelationshipStatus());
                details.setShowAvatar(updatedUser.getDetails().getShowAvatar());
                details.setAvatar(updatedUser.getDetails().getAvatar());
            }
        }

        return userRepository.save(user);
    }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
}

@DeleteMapping("/{userId}")
public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userRepository.deleteById(userId);
    return ResponseEntity.ok().build();
}

@GetMapping("/{userId}/friends")
public ResponseEntity<List<User>> getFriends(@PathVariable Long userId) {
    List<User> friends = userService.getFriends(userId);
    return ResponseEntity.ok(friends);
}

@GetMapping("/{userId}/best-friends")
public ResponseEntity<List<User>> getBestFriends(@PathVariable Long userId) {
    List<User> bestFriends = userService.getBestFriends(userId);
    return ResponseEntity.ok(bestFriends);
}
}