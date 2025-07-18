package com.postgresql.MasChat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.MessageRepository;
import com.postgresql.MasChat.repository.UserRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(Long senderId, Long recipientId, String content) {
        User sender = userRepository.findById(senderId).orElseThrow();
        User recipient = userRepository.findById(recipientId).orElseThrow();
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setSentAt(java.time.LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1).orElseThrow();
        User user2 = userRepository.findById(userId2).orElseThrow();
        return messageRepository.findBySenderAndRecipientOrSenderAndRecipientOrderBySentAt(
            user1, user2, user2, user1
        );
    }

    public List<Message> getAllMessagesForUser(Long userId) {
        return messageRepository.findBySenderIdOrRecipientIdOrderBySentAtDesc(userId, userId);
    }
}
