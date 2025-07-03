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

import com.postgresql.MasChat.model.Attachment;
import com.postgresql.MasChat.service.AttachmentService;


@RestController
@RequestMapping("/api/attachments" )
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    // Submits an attachment to the database
    @PostMapping("/save")
    public ArrayList<Attachment> submitAttachment(@RequestBody Attachment body) {
        ArrayList<Attachment> result = attachmentService.submitAttachmentToDB(body);
        return result;
    }

    // Retrieves all attachments from the database
    @GetMapping("/getpost")
    public ArrayList<Attachment> retrieveAllAttachments() {
        ArrayList<Attachment> result = attachmentService.retrieveAttachmentsFromDB();
        return result;
    }

    // Deletes a particular attachment from the database
    @DeleteMapping("/delete/{attachmentID}")
    public ArrayList<Attachment> deleteParticularAttachment(@PathVariable UUID attachmentid) {
        ArrayList<Attachment> result = attachmentService.deleteAttachmentFromDB(attachmentid);
        return result;
    }
    
}