package com.postgresql.MasChat.repository;

import com.postgresql.MasChat.model.Story;
import com.postgresql.MasChat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByUser(User user);
    List<Story> findByCreatedAtAfter(java.time.LocalDateTime since);
} 