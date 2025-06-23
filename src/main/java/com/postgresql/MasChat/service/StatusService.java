package com.postgresql.MasChat.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Status;
import com.postgresql.MasChat.repository.StatusRepo;

@Service
public class StatusService {

    @Autowired
    StatusRepo statusRepo;

    public Status saveStatus(Status status) { 
        status.setStatusID(UUID.randomUUID()); // Generate a new UUID for the status ID
       Timestamp uploadTime = new Timestamp(System.currentTimeMillis());
       status.setUploadTime(uploadTime); // Set the current time as upload time
        return statusRepo.save(status); // Save the status using the repository
    }

    
    public ArrayList<Status> getAllStatuses() {
        // Logic to retrieve all statuses
        return new ArrayList<>(); // Return the list of statuses
    }
    
}