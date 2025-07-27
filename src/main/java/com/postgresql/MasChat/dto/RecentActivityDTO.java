package com.postgresql.MasChat.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecentActivityDTO {
    private String id;
    private String type; // post, like, comment, follow, mention
    private String title;
    private String description;
    private String timestamp;
    private String icon;
    private String color;
    private String userId;
    private String userName;
    private String userAvatar;
    private String relatedContentId;
    private String relatedContentType;
} 