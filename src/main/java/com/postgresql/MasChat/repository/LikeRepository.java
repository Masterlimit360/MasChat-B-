package com.postgresql.MasChat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.postgresql.MasChat.model.Like;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT l FROM Like l WHERE l.post.user.id = :postUserId ORDER BY l.createdAt DESC")
    List<Like> findTop10ByPostUserIdOrderByCreatedAtDesc(@Param("postUserId") Long postUserId);
    
    @Query("SELECT l FROM Like l WHERE l.post.id = :postId")
    List<Like> findByPostId(@Param("postId") Long postId);
    
    @Query("SELECT l FROM Like l WHERE l.comment.id = :commentId")
    List<Like> findByCommentId(@Param("commentId") Long commentId);
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.user.id = :postUserId")
    long countByPostUserId(@Param("postUserId") Long postUserId);
} 