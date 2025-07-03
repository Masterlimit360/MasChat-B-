package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Attachment;
import com.postgresql.MasChat.repository.AttachmentRepository;

@Service
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    // Submits an attachment to the database
    public ArrayList<Attachment> submitAttachmentToDB(Attachment attachmentData) {
        attachmentRepository.save(attachmentData);
        ArrayList<Attachment> result = retrieveAttachmentsFromDB();
        return result;
    }

    // Retrieves all attachments from the database
    public ArrayList<Attachment> retrieveAttachmentsFromDB() {
        ArrayList<Attachment> result = (ArrayList<Attachment>) attachmentRepository.findAll();
        return result;
    }

    // Deletes a particular attachment from the database
    public ArrayList<Attachment> deleteAttachmentFromDB(UUID attachmentid) {
        attachmentRepository.deleteById(attachmentid);
        ArrayList<Attachment> result = retrieveAttachmentsFromDB();
        return result;
    }

    public ArrayList<Attachment> deleteAttachmentsFromDB(UUID attachmentid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}