package com.postgresql.MasChat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.dto.UserDTO;
import com.postgresql.MasChat.dto.FriendRequestDTO;
import com.postgresql.MasChat.service.FriendService;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @GetMapping("/suggestions/{userId}")
    public List<UserDTO> getSuggestions(@PathVariable Long userId) {
        return friendService.getSuggestions(userId).stream().map(UserDTO::fromEntity).toList();
    }

    @GetMapping("/requests/{userId}")
    public List<FriendRequestDTO> getFriendRequests(@PathVariable Long userId) {
        return friendService.getFriendRequests(userId).stream().map(FriendRequestDTO::fromEntity).toList();
    }

    @PostMapping("/request")
    public void sendFriendRequest(@RequestParam Long senderId, @RequestParam Long recipientId) {
        friendService.sendFriendRequest(senderId, recipientId);
    }

    @PostMapping("/accept")
    public void acceptFriendRequest(@RequestParam Long requestId) {
        friendService.acceptFriendRequest(requestId);
    }

    @GetMapping("/list/{userId}")
    public List<UserDTO> getFriends(@PathVariable Long userId) {
        return friendService.getFriends(userId).stream().map(UserDTO::fromEntity).toList();
    }

    @DeleteMapping("/request/{requestId}")
    public void deleteFriendRequest(@PathVariable Long requestId) {
        friendService.deleteFriendRequest(requestId);
    }
}
