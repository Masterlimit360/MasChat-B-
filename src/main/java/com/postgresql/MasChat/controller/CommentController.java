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

import com.postgresql.MasChat.model.Comment;
import com.postgresql.MasChat.service.CommentService;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // Submits a comment to the database
    @PostMapping("/save")
    public ArrayList<Comment> submitComment(@RequestBody Comment body) {
        ArrayList<Comment> result = commentService.submitCommentToDB(body);
        return result;
    }

    // Retrieves all comments from the database
    @GetMapping("/getcomments")
    public ArrayList<Comment> retrieveAllComments() {
        ArrayList<Comment> result = commentService.retrieveCommentsFromDB();
        return result;
    }

    // Deletes a particular comment from the database
    @DeleteMapping("/delete/{commentid}")
    public ArrayList<Comment> deleteParticularComment(@PathVariable UUID commentid) {
        ArrayList<Comment> result = commentService.deleteCommentFromDB(commentid);
        return result;
    }
}