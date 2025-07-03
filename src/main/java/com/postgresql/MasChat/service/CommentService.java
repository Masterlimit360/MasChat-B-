package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Comment;
import com.postgresql.MasChat.repository.CommentRepository;



@Service
public class CommentService {
     @Autowired
    private CommentRepository commentRepository;

    // Submits a comment to the database
    public ArrayList<Comment> submitCommentToDB(Comment commentData) {
        commentRepository.save(commentData);
        ArrayList<Comment> result = retrieveCommentsFromDB();
        return result;
    }

    // Retrieves all comments from the database
    public ArrayList<Comment> retrieveCommentsFromDB() {
        ArrayList<Comment> result = (ArrayList<Comment>) commentRepository.findAll();
        return result;
    }

    // Deletes a particular comment from the database
    public ArrayList<Comment> deleteCommentFromDB(UUID commentID) {
        commentRepository.deleteById(commentID);
        ArrayList<Comment> result = retrieveCommentsFromDB();
        return result;
    }

    public ArrayList<Comment> deleteCommentsFromDB(UUID commentID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}