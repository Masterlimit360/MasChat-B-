package com.postgresql.MasChat.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Story;
import com.postgresql.MasChat.service.StoryService;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @PostMapping("/save")
    public Story saveStory(@RequestBody Story story) {
        // Logic to save the story
        return storyService.saveStory(story); // Return the saved story
    }

    @GetMapping("/getAllStories")
    public ArrayList<Story> getAllStories() {
        // Logic to retrieve all stories
        return storyService.getAllStories(); // Return the list of stories
    }
}