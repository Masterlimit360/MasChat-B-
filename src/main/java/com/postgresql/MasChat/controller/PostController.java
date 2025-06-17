package com.postgresql.MasChat.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Post;
import com.postgresql.MasChat.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    // Submits a post to the database
    @PostMapping("/save")
    public ArrayList<Post> submitPost(@RequestBody Post body) {
        ArrayList<Post> result = postService.submitPostToDB(body);
        return result;
    }

    // Retrieves all posts from the database
    @GetMapping("/getpost")
    public ArrayList<Post> retrieveAllPosts() {
        ArrayList<Post> result = postService.retrievePostsFromDB();
        return result;
    }

    // Deletes a particular post from the database
    @DeleteMapping("/delete")
    public ArrayList<Post> deleteParticularPost(@PathVariable UUID postID) {
        ArrayList<Post> result = postService.deletePostFromDB(postID);
        return result;
    }

}