package com.postgresql.MasChat.service;

import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.FriendRequestRepository;
import com.postgresql.MasChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public FriendService(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }

    public List<User> getSuggestions(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        List<User> allUsers = userRepository.findAll();
        List<User> friends = user.getFriends();
        List<User> sentRequests = friendRequestRepository.findAll().stream()
            .filter(fr -> fr.getSender().equals(user))
            .map(FriendRequest::getRecipient)
            .toList();
        List<User> receivedRequests = friendRequestRepository.findAll().stream()
            .filter(fr -> fr.getRecipient().equals(user))
            .map(FriendRequest::getSender)
            .toList();
        return allUsers.stream()
            .filter(u -> !u.getId().equals(userId))
            .filter(u -> !friends.contains(u))
            .filter(u -> !sentRequests.contains(u))
            .filter(u -> !receivedRequests.contains(u))
            .toList();
    }

    public List<FriendRequest> getFriendRequests(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        return friendRequestRepository.findAll().stream()
            .filter(fr -> fr.getRecipient().equals(user) && "PENDING".equals(fr.getStatus()))
            .toList();
    }

    public void sendFriendRequest(Long senderId, Long recipientId) {
        User sender = userRepository.findById(senderId).orElse(null);
        User recipient = userRepository.findById(recipientId).orElse(null);
        if (sender == null || recipient == null) return;
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setRecipient(recipient);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        friendRequestRepository.save(request);
    }

    public void acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request == null) return;
        request.setStatus("ACCEPTED");
        friendRequestRepository.save(request);
        User sender = request.getSender();
        User recipient = request.getRecipient();
        sender.getFriends().add(recipient);
        recipient.getFriends().add(sender);
        userRepository.save(sender);
        userRepository.save(recipient);
    }

    public List<User> getFriends(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        return user.getFriends();
    }

    public void deleteFriendRequest(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }
}
