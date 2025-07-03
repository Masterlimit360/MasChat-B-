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
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID commentid;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and setters...

    public Comment() {
        super();
    }

    public Comment(UUID commentid, Post post, User user, String content, LocalDateTime createdAt) {
        this.commentid = commentid;
        this.user = user;
        this.post = post;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getCommentid() {
        return commentid;
    }

    public void setCommentid(UUID commentid) {
        this.commentid = commentid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getpost() {
        return post;
    }

    public void setpost(Post post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void save(Comment commentData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public ArrayList<Comment> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public void deleteById(Long commentid2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
