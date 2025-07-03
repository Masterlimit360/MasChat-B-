package com.postgresql.MasChat.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Story;
import com.postgresql.MasChat.repository.StoryRepository;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public Story saveStory(Story story) {
        story.setStoryID(UUID.randomUUID()); // Generate a new UUID for the story ID
       Timestamp uploadTime = new Timestamp(System.currentTimeMillis());
       story.setUploadTime(uploadTime); // Set the current time as upload time
        return storyRepository.save(story); // Save the story using the repository
    }

    public ArrayList<Story> getAllStories() {
        // Logic to retrieve all stories
        return new ArrayList<>(storyRepository.findAll());
    }
}