package com.postgresql.MasChat.controller;

import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Notification;
import com.postgresql.MasChat.service.NotificationService;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    // Submits a notification to the database
    @PostMapping("/save")
    public ArrayList<Notification> submitNotification(@RequestBody Notification body) {
        ArrayList<Notification> result = notificationService.submitNotificationToDB(body);
        return result;
    }

    // Retrieves all notifications from the database
    @GetMapping("/getnotifications")
    public ArrayList<Notification> retrieveAllNotifications() {
        ArrayList<Notification> result = notificationService.retrieveNotificationsFromDB();
        return result;
    }

    // Deletes a particular post from the database
    @DeleteMapping("/delete/{notificationID}")
    public ArrayList<Notification> deleteParticularNotification(@PathVariable UUID notificationID) {
        ArrayList<Notification> result = notificationService.deleteNotificationFromDB(notificationID); //notificationService.deletePostFromDB(postID);
        return result;
    }
}