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

import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.service.FriendRequestService;

@RestController
@RequestMapping("/api/friendRequests")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService;

    // Submits a friend request to the database
    @PostMapping("/save")
    public ArrayList<FriendRequest> submitLikes(@RequestBody FriendRequest body) {
        ArrayList<FriendRequest> result = friendRequestService.submitFriendRequestToDB(body);
        return result;
    }

    // Retrieves all friend requests from the database
    @GetMapping("/getfriendRequests")
    public ArrayList<FriendRequest> retrieveAllFriendRequests() {
        ArrayList<FriendRequest> result = friendRequestService.retrievefriendRequestsFromDB();
        return result;
    }

     // Deletes a particular friend request from the database
    @DeleteMapping("/delete/{friendRequestid}")
    public ArrayList<FriendRequest> deleteParticularFriendRequest(@PathVariable UUID friendRequestid) {
        ArrayList<FriendRequest> result = friendRequestService.deletefriendRequestFromDB(friendRequestid);
        return result;
    }
}