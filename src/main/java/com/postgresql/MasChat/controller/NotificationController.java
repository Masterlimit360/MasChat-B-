package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.model.Notification;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.UserRepository;
import com.postgresql.MasChat.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();
        return notificationService.getNotifications(user);
    }

    @PostMapping("/read/{notificationId}")
    public void markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
    }
} 