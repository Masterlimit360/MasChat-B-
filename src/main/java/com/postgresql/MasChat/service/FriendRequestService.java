package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.repository.FriendRequestRepository;



@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    // Submits a Friend request to the database
    public ArrayList<FriendRequest> submitMessageToDB(FriendRequest friendRequestData) {
        friendRequestRepository.save(friendRequestData);
        ArrayList<FriendRequest> result = retrievefriendRequestsFromDB();
        return result;
    }

        // Retrieves all friend requests from the database
    public ArrayList<FriendRequest> retrievefriendRequestsFromDB() {
        ArrayList<FriendRequest> result = (ArrayList<FriendRequest>) friendRequestRepository.findAll();
        return result;
    }

    // Deletes a particular friend request from the database
    public ArrayList<FriendRequest> deletefriendRequestFromDB(UUID friendRequestid) {
        friendRequestRepository.deleteById(friendRequestid);
        ArrayList<FriendRequest> result = retrievefriendRequestsFromDB();
        return result;
    }

    public ArrayList<FriendRequest> submitFriendRequestToDB(FriendRequest body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'submitLikeToDB'");
    }
}