package com.postgresql.MasChat.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.model.User;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByRecipientAndStatus(User recipient, String status);
    List<FriendRequest> findBySenderAndStatus(User sender, String status);
}