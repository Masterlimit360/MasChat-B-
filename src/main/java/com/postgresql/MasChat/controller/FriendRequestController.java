package com.postgresql.MasChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.service.FriendRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping("/api/friend-requests/request")
    public ResponseEntity<?> sendRequest(
        @RequestParam Long senderId,
        @RequestParam Long recipientId
    ) {
        FriendRequest request = friendRequestService.sendRequest(senderId, recipientId);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/respond")
    public ResponseEntity<FriendRequest> respondToRequest(
        @RequestParam Long requestId,
        @RequestParam String status // "accepted" or "rejected"
    ) {
        FriendRequest request = friendRequestService.respondToRequest(requestId, status);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendRequest>> getPendingRequests(
        @RequestParam Long userId
    ) {
        List<FriendRequest> requests = friendRequestService.getPendingRequests(userId);
        return ResponseEntity.ok(requests);
    }
}