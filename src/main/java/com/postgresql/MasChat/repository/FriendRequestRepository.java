package com.postgresql.MasChat.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.postgresql.MasChat.model.FriendRequest;
import com.postgresql.MasChat.model.User;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverAndStatus(User receiver, String status);
    List<FriendRequest> findBySenderAndStatus(User sender, String status);
    List<FriendRequest> findBySenderId(Long senderId);
    List<FriendRequest> findByReceiverId(Long receiverId);
    List<FriendRequest> findByReceiverIdAndStatus(Long receiverId, String status);
}