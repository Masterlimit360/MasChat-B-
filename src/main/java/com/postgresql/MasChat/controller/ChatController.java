package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.dto.ChatMessage;
import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.MessageRepository;
import com.postgresql.MasChat.repository.UserRepository;
import com.postgresql.MasChat.service.MessageService;
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
import java.util.Map;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MessageService messageService;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        try {
            // Save to DB using MessageService
            Message savedMessage = messageService.sendMessage(
                Long.valueOf(chatMessage.getSenderId()),
                Long.valueOf(chatMessage.getRecipientId()),
                chatMessage.getContent()
            );
            
            logger.info("Message saved: {} -> {}: {}", chatMessage.getSenderId(), chatMessage.getRecipientId(), chatMessage.getContent());
            
            // Send to users
            messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                savedMessage
            );
            messagingTemplate.convertAndSendToUser(
                chatMessage.getSenderId(),
                "/queue/messages",
                savedMessage
            );
        } catch (Exception e) {
            logger.error("Failed to save or send message: {}", chatMessage, e);
        }
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload Map<String, Object> userInfo) {
        logger.info("User added to chat: {}", userInfo.get("username"));
    }
} 