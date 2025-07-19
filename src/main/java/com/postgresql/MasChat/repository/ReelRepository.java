package com.postgresql.MasChat.repository;

import com.postgresql.MasChat.model.Reel;
import com.postgresql.MasChat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Long> {
    List<Reel> findByUser(User user);
    List<Reel> findByCreatedAtAfter(java.time.LocalDateTime since);
    List<Reel> findByCaptionContainingIgnoreCase(String caption);
} 