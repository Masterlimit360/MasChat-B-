package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Message;
import com.postgresql.MasChat.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Submits a message to the database
    public ArrayList<Message> submitMessageToDB(Message messageData) {
        messageRepository.save(messageData);
        ArrayList<Message> result = retrieveMessagesFromDB();
        return result;
    }

    private ArrayList<Message> retrieveMessagesFromDB() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveMessagesFromDB'");
    }

    // Retrieves all messages from the database
    public ArrayList<Message> retrievePostsFromDB() {
        ArrayList<Message> result = (ArrayList<Message>) messageRepository.findAll();
        return result;
    }

    // Deletes a particular message from the database
    public ArrayList<Message> deleteMessageFromDB(UUID messageid) {
        messageRepository.deleteById(messageid);
        ArrayList<Message> result = retrieveMessagesFromDB();
        return result;
    }


}