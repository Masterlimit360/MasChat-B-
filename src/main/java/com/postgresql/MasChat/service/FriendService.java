package com.postgresql.MasChat.service;

import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.FriendRequestRepository;
import com.postgresql.MasChat.repository.UserRepository;
import com.postgresql.MasChat.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public FriendService(FriendRequestRepository friendRequestRepository, UserRepository userRepository, NotificationService notificationService) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public List<User> getSuggestions(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        
        // Get IDs of friends
        List<Long> friendIds = user.getFriends().stream().map(User::getId).toList();
        
        // Get IDs of users to whom this user has sent requests
        List<Long> sentRequestIds = friendRequestRepository.findBySenderId(userId).stream()
                .map(fr -> fr.getReceiver().getId()).toList();
        
        // Get IDs of users who have sent requests to this user
        List<Long> receivedRequestIds = friendRequestRepository.findByReceiverId(userId).stream()
                .map(fr -> fr.getSender().getId()).toList();
        
        // Get all users except self, friends, sent/received requests
        return userRepository.findAll().stream()
                .filter(u -> !u.getId().equals(userId))
                .filter(u -> !friendIds.contains(u.getId()))
                .filter(u -> !sentRequestIds.contains(u.getId()))
                .filter(u -> !receivedRequestIds.contains(u.getId()))
                .limit(20) // Limit to 20 suggestions
                .toList();
    }

    public List<FriendRequest> getFriendRequests(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        // Get only pending requests for this user
        return friendRequestRepository.findByReceiverIdAndStatus(userId, "PENDING");
    }

    public void sendFriendRequest(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).orElse(null);
        User receiver = userRepository.findById(receiverId).orElse(null);
        if (sender == null || receiver == null) return;
        
        // Check if request already exists
        List<FriendRequest> existingRequests = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (!existingRequests.isEmpty()) return; // Request already exists
        
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        friendRequestRepository.save(request);
        
        // Notify receiver
        notificationService.createNotification(receiver, sender.getFullName() + " sent you a friend request.");
    }

    public void acceptFriendRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElse(null);
        if (request == null) return;
        
        request.setStatus("ACCEPTED");
        friendRequestRepository.save(request);
        
        User sender = request.getSender();
        User receiver = request.getReceiver();
        
        // Add to friends list
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);
        userRepository.save(sender);
        userRepository.save(receiver);
        
        // Notify sender that request was accepted
        notificationService.createNotification(sender, receiver.getFullName() + " accepted your friend request.");
        // Optionally, mark the original notification as read or update it
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
