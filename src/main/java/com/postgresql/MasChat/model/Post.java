package com.postgresql.MasChat.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Table(name = "posts")
public class Post {
    @Id
    private UUID postID;
    private UUID userID;
    private String userName;
    private String imageURL;
    private String description;
    private String postImageURL;
    private String postVideoURL;
    private int likes;
    private Timestamp dateTime;

    public Post() {
        super();
    }

    // Default constructor
    public Post(UUID postID, UUID userID, String userName, String imageURL, String description,
            String postImageURL, String postVideoURL, String postAudioURL, int likes, Timestamp dateTime) {
        this.postID = postID;
        this.userID = userID;
        this.userName = userName;
        this.imageURL = imageURL;
        this.description = description;
        this.postImageURL = postImageURL;
        this.postVideoURL = postVideoURL;
        this.likes = likes;
        this.dateTime = dateTime;
    }

    public void setPostID(UUID postID) {
        this.postID = postID;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public String getPostVideoURL() {
        return postVideoURL;
    }

    public void setPostVideoURL(String postVideoURL) {
        this.postVideoURL = postVideoURL;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public void save(Post postData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public ArrayList<Post> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public void deleteById(UUID postID2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}