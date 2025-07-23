package com.postgresql.MasChat.service;

import com.postgresql.MasChat.model.*;
import com.postgresql.MasChat.repository.*;
import com.postgresql.MasChat.dto.PostRequestDto;
import com.postgresql.MasChat.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Post createPost(PostRequestDto dto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setImageUrl(dto.getImageUrl());
        post.setVideoUrl(dto.getVideoUrl());
        post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public Post likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        if (!post.getLikedBy().contains(user)) {
            post.getLikedBy().add(user);
        }
        return postRepository.save(post);
    }

    public Post unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        post.getLikedBy().remove(user);
        return postRepository.save(post);
    }

    public Comment addComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content); // Use setContent instead of setText
        comment.setCreatedAt(java.time.LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Post> searchPosts(String query) {
        return postRepository.findByContentContainingIgnoreCase(query);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow();
        // Check if the user is the owner of the post
        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("User not authorized to delete this post");
        }
        postRepository.delete(post);
    }

    public java.util.List<CommentDTO> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        java.util.List<Comment> comments = commentRepository.findByPost(post);
        java.util.List<CommentDTO> dtos = new java.util.ArrayList<>();
        for (Comment c : comments) {
            dtos.add(CommentDTO.fromEntity(c));
        }
        return dtos;
    }
}