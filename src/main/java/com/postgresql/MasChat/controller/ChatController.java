package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.dto.ChatMessage;
import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.MessageRepository;
import com.postgresql.MasChat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        try {
            // Save to DB
            Message message = new Message();
            message.setSender(userRepository.findById(Long.valueOf(chatMessage.getSenderId())).orElseThrow());
            message.setRecipient(userRepository.findById(Long.valueOf(chatMessage.getRecipientId())).orElseThrow());
            message.setContent(chatMessage.getContent());
            // Parse ISO 8601 with Z (UTC) support
            Instant instant = Instant.parse(chatMessage.getTimestamp());
            message.setSentAt(java.time.LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
            messageRepository.save(message);
            logger.info("Message saved: {} -> {}: {}", chatMessage.getSenderId(), chatMessage.getRecipientId(), chatMessage.getContent());
            // Send to users
            messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                chatMessage
            );
            messagingTemplate.convertAndSendToUser(
                chatMessage.getSenderId(),
                "/queue/messages",
                chatMessage
            );
        } catch (Exception e) {
            logger.error("Failed to save or send message: {}", chatMessage, e);
        }
    }
} 