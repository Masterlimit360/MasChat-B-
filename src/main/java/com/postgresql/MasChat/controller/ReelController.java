
package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.dto.ReelDTO;
import com.postgresql.MasChat.model.Reel;
import com.postgresql.MasChat.model.ReelComment;
import com.postgresql.MasChat.service.ReelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reels")
public class ReelController {

    @GetMapping("/{reelId}")
    public ReelDTO getReelById(@PathVariable Long reelId) {
        Reel reel = reelService.getReelById(reelId);
        return ReelDTO.fromEntity(reel);
    }
    @Autowired
    private ReelService reelService;

    @GetMapping
    public List<ReelDTO> getRecentReels() {
        return reelService.getRecentReels().stream().map(ReelDTO::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ReelDTO> searchReels(@RequestParam String query) {
        return reelService.searchReels(query).stream().map(ReelDTO::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ReelDTO createReel(@RequestBody ReelCreateRequest req) {
        Reel reel = reelService.createReel(req.getUserId(), req.getMediaUrl(), req.getCaption());
        return ReelDTO.fromEntity(reel);
    }

    @PostMapping("/{reelId}/like")
    public ReelDTO likeReel(@PathVariable Long reelId, @RequestParam Long userId) {
        return ReelDTO.fromEntity(reelService.likeReel(reelId, userId));
    }

    @PostMapping("/{reelId}/unlike")
    public ReelDTO unlikeReel(@PathVariable Long reelId, @RequestParam Long userId) {
        return ReelDTO.fromEntity(reelService.unlikeReel(reelId, userId));
    }

    @PostMapping("/{reelId}/comment")
    public ReelComment addComment(@PathVariable Long reelId, @RequestParam Long userId, @RequestBody String content) {
        return reelService.addComment(reelId, userId, content);
    }

    @PostMapping("/{reelId}/share")
    public ReelDTO shareReel(@PathVariable Long reelId) {
        return ReelDTO.fromEntity(reelService.shareReel(reelId));
    }

    @DeleteMapping("/{reelId}")
    public void deleteReel(@PathVariable Long reelId, @RequestParam Long userId) {
        reelService.deleteReel(reelId, userId);
    }

    public static class ReelCreateRequest {
        private Long userId;
        private String mediaUrl;
        private String caption;
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getMediaUrl() { return mediaUrl; }
        public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
        public String getCaption() { return caption; }
        public void setCaption(String caption) { this.caption = caption; }
    }
} 