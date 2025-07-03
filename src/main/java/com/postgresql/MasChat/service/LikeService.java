package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Like;
import com.postgresql.MasChat.repository.LikeRepository;



@Service
public class LikeService {
     @Autowired
    private LikeRepository likeRepository;

    // Submits a like to the database
    public ArrayList<Like> submitMessageToDB(Like likeData) {
        likeRepository.save(likeData);
        ArrayList<Like> result = retrieveLikesFromDB();
        return result;
    }

    private ArrayList<Like> retrieveLikesFromDB() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveMessagesFromDB'");
    }

    // Retrieves all likes from the database
    public ArrayList<Like> retrievelikesFromDB() {
        ArrayList<Like> result = (ArrayList<Like>) likeRepository.findAll();
        return result;
    }

    // Deletes a particular like from the database
    public ArrayList<Like> deleteLikeFromDB(UUID likeid) {
        likeRepository.deleteById(likeid);
        ArrayList<Like> result = retrieveLikesFromDB();
        return result;
    }

    public ArrayList<Like> submitLikeToDB(Like body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'submitLikeToDB'");
    }
}