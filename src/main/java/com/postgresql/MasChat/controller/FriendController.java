package com.postgresql.MasChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.service.FriendService;
import com.postgresql.MasChat.dto.FriendRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long userId) {
        List<User> friends = friendService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/suggestions/{userId}")
    public ResponseEntity<List<User>> getFriendSuggestions(@PathVariable Long userId) {
        List<User> suggestions = friendService.getSuggestions(userId);
        return ResponseEntity.ok(suggestions);
    }

    @PostMapping("/request")
    public ResponseEntity<Void> sendFriendRequest(
        @RequestParam Long senderId,
        @RequestParam Long recipientId
    ) {
        friendService.sendFriendRequest(senderId, recipientId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long requestId) {
        friendService.acceptFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/request/{requestId}")
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable Long requestId) {
        friendService.deleteFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<FriendRequestDTO>> getPendingRequests(@PathVariable Long userId) {
        List<FriendRequest> requests = friendService.getFriendRequests(userId);
        List<FriendRequestDTO> dtos = requests.stream()
            .map(FriendRequestDTO::fromEntity)
            .toList();
        return ResponseEntity.ok(dtos);
    }
}
