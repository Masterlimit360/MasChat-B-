package com.postgresql.MasChat.model;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID storyID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userID;

    @ManyToOne
    @JoinColumn(name = "user_name", nullable = false)
    private User username;

    private String storyImageURL;
    private String storyVideoURL;
    private Timestamp uploadTime;

    public Story() {
        super();
    }

    public Story(UUID storyID, User userID, User username, String storyImageURL, String storyVideoURL, Timestamp uploadTime) {
        super();
        this.storyID = storyID;
        this.userID = userID;
        this.username = username;
        this.storyImageURL = storyImageURL;
        this.storyVideoURL = storyVideoURL;
        this.uploadTime = uploadTime;
    }

    public UUID getStoryID() {
        return storyID;
    }

    public void setStoryID(UUID storyID) {
        this.storyID = storyID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getStoryImageURL() {
        return storyImageURL;
    }

    public void setStoryImageURL(String storyImageURL) {
        this.storyImageURL = storyImageURL;
    }

    public String getStoryVideoURL() {
        return storyVideoURL;
    }

    public void setStoryVideoURL(String storyVideoURL) {
        this.storyVideoURL = storyVideoURL;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }
}