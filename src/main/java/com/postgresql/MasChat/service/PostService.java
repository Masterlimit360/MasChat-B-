package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Post;
import com.postgresql.MasChat.repository.PostRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepo;

    // Submits a post to the database
    public ArrayList<Post> submitPostToDB(Post postData) {
        postRepo.save(postData);
        ArrayList<Post> result = retrievePostsFromDB();
        return result;
    }

    // Retrieves all posts from the database
    public ArrayList<Post> retrievePostsFromDB() {
        ArrayList<Post> result = (ArrayList<Post>) postRepo.findAll();
        return result;
    }

    // Deletes a particular post from the database
    public ArrayList<Post> deletePostFromDB(UUID postID) {
        postRepo.deleteById(postID);
        ArrayList<Post> result = retrievePostsFromDB();
        return result;
    }

    public ArrayList<Post> deletePostsFromDB(UUID postID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}