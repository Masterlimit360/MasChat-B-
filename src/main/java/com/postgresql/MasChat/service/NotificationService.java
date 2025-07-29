package com.postgresql.MasChat.service;

import com.postgresql.MasChat.model.Notification;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.NotificationRepository;
import com.postgresql.MasChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.data.domain.PageRequest;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Notification createNotification(User user, String message) {
        return createNotification(user, "Notification", message, Notification.NotificationType.SYSTEM_MESSAGE);
    }

    public Notification createNotification(User user, String title, String message, Notification.NotificationType type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setRead(false);
        notification.setCreatedAt(java.time.LocalDateTime.now());
        Notification saved = notificationRepository.save(notification);
        
        // Send via WebSocket
        try {
            messagingTemplate.convertAndSendToUser(
                user.getId().toString(),
                "/queue/notifications",
                saved
            );
        } catch (Exception e) {
            // Log WebSocket error but don't fail the notification creation
            System.err.println("WebSocket notification failed: " + e.getMessage());
        }
        
        return saved;
    }

    public Notification createFriendRequestNotification(User sender, User receiver) {
        return createNotification(
            receiver,
            "Friend Request",
            sender.getFullName() + " sent you a friend request.",
            Notification.NotificationType.FRIEND_REQUEST
        );
    }

    public Notification createFriendRequestAcceptedNotification(User receiver, User sender) {
        return createNotification(
            sender,
            "Friend Request Accepted",
            receiver.getFullName() + " accepted your friend request.",
            Notification.NotificationType.FRIEND_REQUEST
        );
    }

    public List<Notification> getNotifications(User user) {
        // Use a default Pageable for the latest 50 notifications
        return notificationRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(user.getId(), PageRequest.of(0, 50)).getContent();
    }

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserIdAndReadFalseAndDeletedFalseOrderByCreatedAtDesc(user.getId());
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
            // Optionally, send update via WebSocket
            messagingTemplate.convertAndSendToUser(
                notification.getUser().getId().toString(),
                "/queue/notifications",
                notification
            );
        }
    }

    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
} 