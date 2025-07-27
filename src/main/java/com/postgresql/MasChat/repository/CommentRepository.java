package com.postgresql.MasChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.postgresql.MasChat.model.Comment;
import com.postgresql.MasChat.model.Post;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT c FROM Comment c WHERE c.post.user.id = :postUserId ORDER BY c.createdAt DESC")
    List<Comment> findTop10ByPostUserIdOrderByCreatedAtDesc(@Param("postUserId") Long postUserId);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.user.id = :postUserId")
    long countByPostUserId(@Param("postUserId") Long postUserId);
}
