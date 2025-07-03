package com.postgresql.MasChat.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID notificationID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userID;

    @Column(nullable = false)
    private String message;

    private String userName;

    private boolean read = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and setters...

    public Notification() {
        super();
    }

    public Notification(UUID notificationID, User userID, String message, String userName, boolean read,
            LocalDateTime createdAt) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.message = message;
        this.userName = userName;
        this.read = read;
        this.createdAt = createdAt;
    }

    public UUID getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(UUID notificationID) {
        this.notificationID = notificationID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void save(Notification notificationData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public ArrayList<Notification> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public void deleteById(UUID notificationID2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
    // Getters and setters...
}
