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

import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.service.MessageService;



@RestController
@RequestMapping("/api/messages")    
public class MessageController {
    @Autowired
    private MessageService messageService;

    // Submits a message to the database
    @PostMapping("/save")
    public ArrayList<Message> submitMessages(@RequestBody Message body) {
        ArrayList<Message> result = messageService.submitMessageToDB(body);
        return result;
    }

    // Retrieves all messages from the database
    @GetMapping("/getmessages")
    public ArrayList<Message> retrieveAllMessages() {
        ArrayList<Message> result = messageService.retrievePostsFromDB();
        return result;
    }

    // Deletes a particular message from the database
    @DeleteMapping("/delete/{messageid}")
    public ArrayList<Message> deleteParticularMessage(@PathVariable UUID messageid) {
        ArrayList<Message> result = messageService.deleteMessageFromDB(messageid);
        return result;
    }
    
}