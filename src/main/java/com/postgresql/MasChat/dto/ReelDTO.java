package com.postgresql.MasChat.dto;

import com.postgresql.MasChat.model.Reel;

public class ReelDTO {
    private Long id;
    private Long userId;
    private String username;
    private String profilePicture;
    private String mediaUrl;
    private String caption;
    private String createdAt;

    public static ReelDTO fromEntity(Reel reel) {
        ReelDTO dto = new ReelDTO();
        dto.setId(reel.getId());
        dto.setUserId(reel.getUser().getId());
        dto.setUsername(reel.getUser().getUsername());
        dto.setProfilePicture(reel.getUser().getProfilePicture());
        dto.setMediaUrl(reel.getMediaUrl());
        dto.setCaption(reel.getCaption());
        dto.setCreatedAt(reel.getCreatedAt().toString());
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }
    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
} 