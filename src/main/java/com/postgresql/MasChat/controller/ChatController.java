package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.dto.ChatMessage;
import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.MessageRepository;
import com.postgresql.MasChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {
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
        // Save to DB
        Message message = new Message();
        message.setSender(userRepository.findById(Long.valueOf(chatMessage.getSenderId())).orElseThrow());
        message.setRecipient(userRepository.findById(Long.valueOf(chatMessage.getRecipientId())).orElseThrow());
        message.setContent(chatMessage.getContent());
        message.setSentAt(LocalDateTime.parse(chatMessage.getTimestamp()));
        messageRepository.save(message);

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
    }
} 