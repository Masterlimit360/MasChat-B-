package com.postgresql.MasChat.dto;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    private Long totalPosts;
    private Long totalLikes;
    private Long totalComments;
    private Long totalShares;
    private Long followers;
    private Long following;
    private Long profileViews;
    private Double engagementRate;
    private Double weeklyGrowth;
    private Double monthlyGrowth;
    private Long totalMemories;
    private Long thisYearMemories;
    private Long thisMonthMemories;
    private Long thisWeekMemories;
    private Long totalSaved;
    private Long postsSaved;
    private Long storiesSaved;
    private Long reelsSaved;
    private Long articlesSaved;
    private Long videosSaved;
    private Long totalGroups;
    private Long myGroups;
    private Long pendingInvites;
    private Long totalMembers;
    private Long totalCampaigns;
    private Long activeCampaigns;
    private Double totalSpent;
    private Long totalImpressions;
    private Long totalClicks;
    private Double averageCTR;
    private Double averageCPC;
} 