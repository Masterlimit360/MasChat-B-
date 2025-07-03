package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Notification;
import com.postgresql.MasChat.repository.NotificationRepository;


@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    // Submits a notification to the database
    public ArrayList<Notification> submitNotificationToDB(Notification notificationData) {
        notificationRepository.save(notificationData);
        ArrayList<Notification> result = retrieveNotificationsFromDB();
        return result;
    }

    // Retrieves all notifications from the database
    public ArrayList<Notification> retrieveNotificationsFromDB() {
        ArrayList<Notification> result = (ArrayList<Notification>) notificationRepository.findAll();
        return result;
    }

    // Deletes a particular notification from the database
    public ArrayList<Notification> deleteNotificationFromDB(UUID notificationID) {
        notificationRepository.deleteById(notificationID);
        ArrayList<Notification> result = retrieveNotificationsFromDB();
        return result;
    }

}