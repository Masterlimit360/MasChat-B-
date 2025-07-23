package com.postgresql.MasChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresql.MasChat.model.Comment;
import com.postgresql.MasChat.model.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    java.util.List<Comment> findByPost(Post post);
}
