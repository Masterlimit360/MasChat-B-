package com.postgresql.MasChat.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.MasChat.model.Status;
import com.postgresql.MasChat.service.StatusService;

@RestController
@RequestMapping("/api/post")
public class StatusController {
    @Autowired
    private StatusService statusService;
    
    @PostMapping("/save")
    public Status saveStatus(@RequestBody Status status) {
        // Logic to save the status
        return statusService.saveStatus(status); // Return the saved status
    }

    @GetMapping("/getAllStatus")
    public ArrayList<Status> getAllStatuses() {
        // Logic to retrieve all statuses
        return statusService.getAllStatuses(); // Return the list of statuses
    }
}