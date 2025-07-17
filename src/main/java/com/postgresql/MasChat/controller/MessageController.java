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
        List<Message> messages = messageService.getConversation(userId1, userId2);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/recent/{userId}")
    public List<RecentChatDTO> getRecentChats(@PathVariable Long userId) {
        // Example logic: get distinct users the user has chatted with, with last message and time
        // You should optimize this for your schema
        List<RecentChatDTO> result = new ArrayList<>();
        List<Message> messages = messageService.getAllMessagesForUser(userId); // implement this in your service
        Map<Long, Message> lastMessageMap = new HashMap<>();
        for (Message msg : messages) {
            Long otherId = msg.getSender().getId().equals(userId) ? msg.getRecipient().getId() : msg.getSender().getId();
            if (!lastMessageMap.containsKey(otherId) || msg.getSentAt().isAfter(lastMessageMap.get(otherId).getSentAt())) {
                lastMessageMap.put(otherId, msg);
            }
        }
        for (Message msg : lastMessageMap.values()) {
            User other = msg.getSender().getId().equals(userId) ? msg.getRecipient() : msg.getSender();
            RecentChatDTO dto = new RecentChatDTO();
            dto.setId(other.getId());
            dto.setUsername(other.getUsername());
            dto.setFullName(other.getFullName());
            dto.setProfilePicture(other.getProfilePicture());
            dto.setLastMessage(msg.getContent());
            dto.setLastMessageTime(msg.getSentAt().toString());
            result.add(dto);
        }
        // Sort by last message time descending
        result.sort((a, b) -> b.getLastMessageTime().compareTo(a.getLastMessageTime()));
        return result;
    }
}
