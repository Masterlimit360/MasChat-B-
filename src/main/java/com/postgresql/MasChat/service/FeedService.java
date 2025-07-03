package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Feed;
import com.postgresql.MasChat.repository.FeedRepository;


@Service
public class FeedService {
    @Autowired
    private FeedRepository feedRepository;

    // Submits a feed to the database
    public ArrayList<Feed> submitFeedToDB(Feed feedData) {
        feedRepository.save(feedData);
        ArrayList<Feed> result = retrieveFeedFromDB();
        return result;
    }
    // Retrieves all feeds from the database
    public ArrayList<Feed> retrieveFeedsFromDB(){ 
        ArrayList<Feed> result = (ArrayList<Feed>) feedRepository.findAll();
        return result;
    }

    // Deletes a particular feed from the database
    public ArrayList<Feed> deleteFeedFromDB(UUID feedid) {
        feedRepository.deleteById(feedid);
        ArrayList<Feed> result = retrieveFeedsFromDB();
        return result;
    }

        private ArrayList<Feed> retrieveFeedFromDB() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}