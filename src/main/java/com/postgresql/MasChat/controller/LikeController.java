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

import com.postgresql.MasChat.model.Like;
import com.postgresql.MasChat.service.LikeService;



@RestController
@RequestMapping("/api/likes")
public class LikeController {
     @Autowired
    private LikeService likeService;

    // Submits a like to the database
    @PostMapping("/save")
    public ArrayList<Like> submitLikes(@RequestBody Like body) {
        ArrayList<Like> result = likeService.submitLikeToDB(body);
        return result;
    }

    // Retrieves all likes from the database
    @GetMapping("/getlikes")
    public ArrayList<Like> retrieveAllLikes() {
        ArrayList<Like> result = likeService.retrievelikesFromDB();
        return result;
    }

    // Deletes a particular like from the database
    @DeleteMapping("/delete/{likeid}")
    public ArrayList<Like> deleteParticularLike(@PathVariable UUID likeid) {
        ArrayList<Like> result = likeService.deleteLikeFromDB(likeid);
        return result;
    }
}