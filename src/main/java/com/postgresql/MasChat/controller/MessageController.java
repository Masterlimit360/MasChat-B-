package com.postgresql.MasChat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.service.MessageService;


@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    
    @PostMapping
    public ResponseEntity<Message> sendMessage(
        @RequestBody Message message,
        @AuthenticationPrincipal User sender
    ) {
        message.setSender(sender);
        return ResponseEntity.ok(messageService.sendMessage(message));
    }
    
    @GetMapping("/conversation/{recipientId}")
    public ResponseEntity<List<Message>> getConversation(
        @PathVariable Long recipientId,
        @AuthenticationPrincipal User sender
    ) {
        User recipient = new User();
        recipient.setId(recipientId);
        return ResponseEntity.ok(messageService.getConversation(sender, recipient));
    }
}
