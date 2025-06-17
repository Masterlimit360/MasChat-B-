package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.postgresql.MasChat.model.Post;

@Service

public class PostService {
    @Autowired
    Post postRepo;

    // Submits a post to the database
    public ArrayList<Post> submitPostToDB(Post postData) {
        postRepo.save(postData);
        ArrayList<Post> result = retrivePostFromDB();
        return result;
    }

    // Retrieves all posts from the database
    public ArrayList<Post> retrievePostsFromDB() {
        ArrayList<Post> result = postRepo.findAll();
        return result;
    }

    // Deletes a particular post from the database
    public ArrayList<Post> deletePostFromDB(UUID postID) {
        postRepo.deleteById(postID);
        ArrayList<Post> result = retrivePostFromDB();
        return result;
    }

    private ArrayList<Post> retrivePostFromDB() {
        ArrayList<Post> result = postRepo.findAll();
        return result;
    }
}