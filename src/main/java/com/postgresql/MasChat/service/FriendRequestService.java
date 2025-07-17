package com.postgresql.MasChat.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.FriendRequestRepository;
import com.postgresql.MasChat.repository.UserRepository;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private UserRepository userRepository;

    public FriendRequest sendRequest(Long senderId, Long recipientId) {
        User sender = userRepository.findById(senderId).orElseThrow();
        User recipient = userRepository.findById(recipientId).orElseThrow();
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(recipient);
        request.setStatus("pending");
        request.setCreatedAt(java.time.LocalDateTime.now());
        return friendRequestRepository.save(request);
    }

    public FriendRequest respondToRequest(Long requestId, String status) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(status); // "accepted" or "rejected"
        return friendRequestRepository.save(request);
    }

    public List<FriendRequest> getPendingRequests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return friendRequestRepository.findByReceiverAndStatus(user, "pending");
    }
}