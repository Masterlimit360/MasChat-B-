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
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender or receiver not found");
        }
        
        // Check if they are already friends
        if (sender.getFriends().contains(receiver)) {
            throw new IllegalStateException("Users are already friends");
        }
        
        // Check if request already exists
        List<FriendRequest> existingRequests = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (!existingRequests.isEmpty()) {
            throw new IllegalStateException("Friend request already exists");
        }
        
        // Check if reverse request exists
        List<FriendRequest> reverseRequests = friendRequestRepository.findBySenderIdAndReceiverId(receiverId, senderId);
        if (!reverseRequests.isEmpty()) {
            throw new IllegalStateException("Friend request already exists");
        }
        
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        friendRequestRepository.save(request);
        
        // Notify receiver
        notificationService.createFriendRequestNotification(sender, receiver);
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
        notificationService.createFriendRequestAcceptedNotification(receiver, sender);
    }

    public List<User> getFriends(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        return user.getFriends();
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElse(null);
        User friend = userRepository.findById(friendId).orElse(null);
        if (user == null || friend == null) return;
        
        // Remove from both users' friend lists
        user.getFriends().removeIf(f -> f.getId().equals(friendId));
        friend.getFriends().removeIf(f -> f.getId().equals(userId));
        
        userRepository.save(user);
        userRepository.save(friend);
    }

    public String getFriendRequestStatus(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).orElse(null);
        User receiver = userRepository.findById(receiverId).orElse(null);
        if (sender == null || receiver == null) return "NONE";
        
        // Check if they are already friends
        if (sender.getFriends().contains(receiver)) {
            return "FRIENDS";
        }
        
        // Check if request exists from sender to receiver
        List<FriendRequest> requests = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (!requests.isEmpty()) {
            return "SENT";
        }
        
        // Check if request exists from receiver to sender
        List<FriendRequest> reverseRequests = friendRequestRepository.findBySenderIdAndReceiverId(receiverId, senderId);
        if (!reverseRequests.isEmpty()) {
            return "RECEIVED";
        }
        
        return "NONE";
    }

    public void deleteFriendRequest(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }

    public void cancelFriendRequest(Long senderId, Long receiverId) {
        List<FriendRequest> requests = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (!requests.isEmpty()) {
            friendRequestRepository.deleteAll(requests);
        }
    }

    public void unfriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElse(null);
        User friend = userRepository.findById(friendId).orElse(null);
        if (user == null || friend == null) return;
        user.getFriends().removeIf(f -> f.getId().equals(friendId));
        friend.getFriends().removeIf(f -> f.getId().equals(userId));
        userRepository.save(user);
        userRepository.save(friend);
    }
}
