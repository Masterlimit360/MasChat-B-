package com.postgresql.MasChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresql.MasChat.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
