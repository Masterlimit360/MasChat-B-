package com.postgresql.MasChat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.model.User;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Find messages between two users, ordered by creation time
    List<Message> findBySenderAndRecipientOrSenderAndRecipientOrderBySentAt(
        User sender1, User recipient1, User sender2, User recipient2
    );
}
