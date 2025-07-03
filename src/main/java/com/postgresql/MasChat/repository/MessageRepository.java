package com.postgresql.MasChat.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.postgresql.MasChat.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    // No need to override deleteById unless custom behavior is required
}