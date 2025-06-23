package com.postgresql.MasChat.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "Status")
public class Status {
    @Id
    private UUID id;
    public UUID getId() { return id; }
    public void setId(UUID id) { 
        this.id = id;
    }

    private UUID statusID;

    private UUID userID;
    private String statusImageURL;
    private String statusVideoURL;
    private Timestamp uploadTime;

    public Status() {
        super();
    }

    public Status(UUID statusID, UUID userID, String statusImageURL, String statusVideoURL, Timestamp uploadTime) {
        super();
        this.statusID = statusID;
        this.userID = userID;
        this.statusImageURL = statusImageURL;
        this.statusVideoURL = statusVideoURL;
        this.uploadTime = uploadTime;
    }

    public UUID getStatusID() {
        return statusID;
    }

    public void setStatusID(UUID statusID) {
        this.statusID = statusID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getStatusImageURL() {
        return statusImageURL;
    }

    public void setStatusImageURL(String statusImageURL) {
        this.statusImageURL = statusImageURL;
    }

    public String getStatusVideoURL() {
        return statusVideoURL;
    }

    public void setStatusVideoURL(String statusVideoURL) {
        this.statusVideoURL = statusVideoURL;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(java.sql.Timestamp uploadTime2) {
        this.uploadTime = uploadTime2;
    }
}