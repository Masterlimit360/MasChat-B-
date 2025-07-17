package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.dto.StoryDTO;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stories")
public class StoryController {
    @Autowired
    private UserRepository userRepository;
    // @Autowired private StoryRepository storyRepository; // If you have a Story entity

    @GetMapping("/recent")
    public List<StoryDTO> getRecentStories() {
        // Example: get users who have a story in the last 24 hours
        // You should replace this with your actual story logic
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        return userRepository.findAll().stream()
            .filter(u -> u.getProfilePicture() != null) // Example: has a story
            .map(u -> {
                StoryDTO dto = new StoryDTO();
                dto.setId(u.getId());
                dto.setUsername(u.getUsername());
                dto.setFullName(u.getFullName());
                dto.setProfilePicture(u.getProfilePicture());
                dto.setStoryImage(u.getProfilePicture()); // Example: use profile picture as story
                dto.setCreatedAt(LocalDateTime.now().toString());
                return dto;
            })
            .collect(Collectors.toList());
    }
} 