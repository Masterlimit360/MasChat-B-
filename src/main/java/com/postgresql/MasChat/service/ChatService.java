package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Chat;
import com.postgresql.MasChat.repository.ChatRepository;



@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    // Submits a chat to the database
    public ArrayList<Chat> submitChatToDB(Chat chatData) {
        chatRepository.save(chatData);
        ArrayList<Chat> result = retrieveChatsFromDB();
        return result;
    }

    // Retrieves all chats from the database
    public ArrayList<Chat> retrieveChatsFromDB() {
        ArrayList<Chat> result = (ArrayList<Chat>) chatRepository.findAll();
        return result;
    }

    // Deletes a particular post from the database
    public ArrayList<Chat> deleteChatFromDB(UUID chatid) {
        chatRepository.deleteById(chatid);
        ArrayList<Chat> result = retrieveChatsFromDB();
        return result;
    }

    public ArrayList<Chat> deleteChatsFromDB(UUID chatid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}