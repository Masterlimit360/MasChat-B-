package com.postgresql.MasChat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Comment;
import com.postgresql.MasChat.model.Post;
import com.postgresql.MasChat.dto.PostRequestDto;
import com.postgresql.MasChat.dto.PostDTO;
import com.postgresql.MasChat.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostRequestDto postDto, @RequestParam Long userId) {
        Post post = postService.createPost(postDto, userId);
        return ResponseEntity.status(201).body(PostDTO.fromEntity(post));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts().stream().map(PostDTO::fromEntity).toList());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUser(userId).stream().map(PostDTO::fromEntity).toList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam String query) {
        return ResponseEntity.ok(postService.searchPosts(query).stream().map(PostDTO::fromEntity).toList());
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(postService.likePost(postId, userId));
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<Post> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(postService.unlikePost(postId, userId));
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestParam Long userId, @RequestBody String text) {
        return ResponseEntity.ok(postService.addComment(postId, userId, text));
    }
}
