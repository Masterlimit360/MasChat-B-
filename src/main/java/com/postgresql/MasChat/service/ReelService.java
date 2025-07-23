
package com.postgresql.MasChat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Reel;
import com.postgresql.MasChat.model.ReelComment;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.ReelCommentRepository;
import com.postgresql.MasChat.repository.ReelRepository;
import com.postgresql.MasChat.repository.UserRepository;

@Service
public class ReelService {

    public Reel getReelById(Long reelId) {
        return reelRepository.findById(reelId).orElseThrow();
    }
    @Autowired
    private ReelRepository reelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReelCommentRepository reelCommentRepository;

    public Reel createReel(Long userId, String mediaUrl, String caption) {
        User user = userRepository.findById(userId).orElseThrow();
        Reel reel = new Reel();
        reel.setUser(user);
        reel.setMediaUrl(mediaUrl);
        reel.setCaption(caption);
        reel.setCreatedAt(LocalDateTime.now());
        return reelRepository.save(reel);
    }

    public List<Reel> getRecentReels() {
        LocalDateTime since = LocalDateTime.now().minusDays(7); // Show last 7 days
        return reelRepository.findByCreatedAtAfter(since);
    }

    public void deleteReel(Long reelId, Long userId) {
        Reel reel = reelRepository.findById(reelId).orElseThrow();
        if (!reel.getUser().getId().equals(userId)) throw new RuntimeException("Unauthorized");
        reelRepository.delete(reel);
    }

    public Reel likeReel(Long reelId, Long userId) {
        Reel reel = reelRepository.findById(reelId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        reel.getLikedBy().add(user);
        return reelRepository.save(reel);
    }

    public Reel unlikeReel(Long reelId, Long userId) {
        Reel reel = reelRepository.findById(reelId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        reel.getLikedBy().remove(user);
        return reelRepository.save(reel);
    }

    public ReelComment addComment(Long reelId, Long userId, String content) {
        Reel reel = reelRepository.findById(reelId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        ReelComment comment = new ReelComment();
        comment.setReel(reel);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(java.time.LocalDateTime.now());
        return reelCommentRepository.save(comment);
    }

    public Reel shareReel(Long reelId) {
        Reel reel = reelRepository.findById(reelId).orElseThrow();
        reel.setShareCount(reel.getShareCount() + 1);
        return reelRepository.save(reel);
    }

    public List<Reel> searchReels(String query) {
        return reelRepository.findByCaptionContainingIgnoreCase(query);
    }
}