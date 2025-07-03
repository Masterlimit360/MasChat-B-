package com.postgresql.MasChat.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Feed;
import com.postgresql.MasChat.service.FeedService;


@RestController
@RequestMapping("/api/Feeds")
public class FeedController {
    @Autowired
    private FeedService feedService;

    // Submits a feed to the database
    @PostMapping("/save")
    public ArrayList<Feed> submitFeed(@RequestBody Feed body) {
        ArrayList<Feed> result = feedService.submitFeedToDB(body);
        return result;
    }

    // Retrieves all feeds from the database
    @GetMapping("/getfeed")
    public ArrayList<Feed> retrieveAllFeeds() {
        ArrayList<Feed> result = feedService.retrieveFeedsFromDB();
        return result;
    }

    // Deletes a particular feed from the database
    @DeleteMapping("/delete/{feedid}")
    public ArrayList<Feed> deleteParticularFeed(@PathVariable UUID feedid) {
        ArrayList<Feed> result = feedService.deleteFeedFromDB(feedid);
        return result;
    }

}