package com.postgresql.MasChat.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feeds")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID feedid;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "feed_posts", joinColumns = @JoinColumn(name = "feed_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> posts;

    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters and setters...

    public Feed() {
        super();
    }

    public Feed(UUID feedid, User user, List<Post> posts, LocalDateTime updatedAt) {
        this.feedid = feedid;
        this.user = user;
        this.posts = posts;
        this.updatedAt = updatedAt;
    }

    public UUID getFeedid() {
        return feedid;
    }

    public void setFeedid(UUID feedid) {
        this.feedid = feedid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getposts() {
        return posts;
    }

    public void setposts(List<Post> posts) {
        this.posts = posts;
    }

    public LocalDateTime getupdatedAt() {
        return updatedAt;
    }

    public void setupdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void save(Feed feedData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public ArrayList<Feed> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public void deleteById(Long feedid2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
