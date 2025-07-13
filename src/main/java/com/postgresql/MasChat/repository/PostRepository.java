package com.postgresql.MasChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresql.MasChat.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {}