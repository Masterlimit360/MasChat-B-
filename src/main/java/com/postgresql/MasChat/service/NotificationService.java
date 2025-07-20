package com.postgresql.MasChat.service;

import com.postgresql.MasChat.model.Notification;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.NotificationRepository;
import com.postgresql.MasChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Notification createNotification(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(java.time.LocalDateTime.now());
        Notification saved = notificationRepository.save(notification);
        // Send via WebSocket
        messagingTemplate.convertAndSendToUser(
            user.getId().toString(),
            "/queue/notifications",
            saved
        );
        return saved;
    }

    public List<Notification> getNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndReadFalseOrderByCreatedAtDesc(user);
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