package com.postgresql.MasChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.dto.FriendRequestDTO;
import com.postgresql.MasChat.service.FriendRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendRequestController {
    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping("/respond")
    public ResponseEntity<FriendRequestDTO> respondToRequest(
        @RequestParam Long requestId,
        @RequestParam String status // "accepted" or "rejected"
    ) {
        FriendRequest request = friendRequestService.respondToRequest(requestId, status);
        return ResponseEntity.ok(FriendRequestDTO.fromEntity(request));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendRequestDTO>> getPendingRequests(
        @RequestParam Long userId
    ) {
        List<FriendRequest> requests = friendRequestService.getPendingRequests(userId);
        return ResponseEntity.ok(requests.stream().map(FriendRequestDTO::fromEntity).toList());
    }
}