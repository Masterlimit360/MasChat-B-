package com.postgresql.MasChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.postgresql.MasChat.model.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findByContentContainingIgnoreCase(String content);
}