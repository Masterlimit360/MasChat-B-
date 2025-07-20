package com.postgresql.MasChat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.service.MessageService;
import com.postgresql.MasChat.dto.RecentChatDTO;
import com.postgresql.MasChat.model.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
        @RequestParam Long senderId,
        @RequestParam Long recipientId,
        @RequestParam String content
    ) {
        Message message = messageService.sendMessage(senderId, recipientId, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
        @RequestParam Long userId1,
        @RequestParam Long userId2
    ) {
        List<Message> conversation = messageService.getConversation(userId1, userId2);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/recent/{userId}")
    public ResponseEntity<List<RecentChatDTO>> getRecentChats(@PathVariable Long userId) {
        List<RecentChatDTO> recentChats = messageService.getRecentChats(userId);
        return ResponseEntity.ok(recentChats);
    }

    @PostMapping("/mark-read")
    public ResponseEntity<Void> markMessagesAsRead(
        @RequestParam Long userId,
        @RequestParam Long partnerId
    ) {
        messageService.markMessagesAsRead(userId, partnerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(
        @PathVariable Long messageId,
        @RequestParam Long userId
    ) {
        messageService.deleteMessage(messageId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/conversation")
    public ResponseEntity<Void> deleteConversation(
        @RequestParam Long userId,
        @RequestParam Long partnerId
    ) {
        messageService.deleteConversation(userId, partnerId);
        return ResponseEntity.ok().build();
    }
}
