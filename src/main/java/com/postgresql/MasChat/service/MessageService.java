package com.postgresql.MasChat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getConversation(User sender, User recipient) {
        return messageRepository.findBySenderAndRecipientOrSenderAndRecipientOrderBySentAt(
            sender, recipient, recipient, sender);
    }
}
