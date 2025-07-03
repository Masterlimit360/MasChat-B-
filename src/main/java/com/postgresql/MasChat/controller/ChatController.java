package com.postgresql.MasChat.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Chat;
import com.postgresql.MasChat.service.ChatService;



@RestController
@RequestMapping("/api/chats")
public class ChatController {
     @Autowired
    private ChatService chatService;

    // Submits a chat to the database
    @PostMapping("/save")
    public ArrayList<Chat> submitChat(@RequestBody Chat body) {
        ArrayList<Chat> result = chatService.submitChatToDB(body);
        return result;
    }

    // Retrieves all chats from the database
    @GetMapping("/getchat")
    public ArrayList<Chat> retrieveAllChats() {
        ArrayList<Chat> result = chatService.retrieveChatsFromDB();
        return result;
    }

    // Deletes a particular chat from the database
    @DeleteMapping("/delete/{chatid}")
    public ArrayList<Chat> deleteParticularChat(@PathVariable UUID chatid) {
        ArrayList<Chat> result = chatService.deleteChatFromDB(chatid);
        return result;
    }
}