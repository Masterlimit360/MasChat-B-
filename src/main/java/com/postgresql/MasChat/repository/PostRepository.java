package com.postgresql.MasChat.repository;

import com.postgresql.MasChat.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}