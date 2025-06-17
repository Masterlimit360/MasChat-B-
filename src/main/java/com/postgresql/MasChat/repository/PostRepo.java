package com.postgresql.MasChat.repository;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.postgresql.MasChat.model.Post;

@Repository
public interface PostRepo extends BaseRepo<Post, UUID> {
    ArrayList<Post> findAll();

    Post save(Post post);
}
