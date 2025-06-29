package com.postgresql.MasChat.service;

import com.postgresql.MasChat.model.Post;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // <-- Add this critical annotation
public class PostService {
    
    private final PostRepository postRepository;

    @Autowired // Constructor injection is preferred
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    public Post createPost(Post post, User user) {
        post.setUser(user);
        return postRepository.save(post);
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }
    
    // Optional: Add more methods as needed
    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUserOrderByCreatedAtDesc(user);
    }
}