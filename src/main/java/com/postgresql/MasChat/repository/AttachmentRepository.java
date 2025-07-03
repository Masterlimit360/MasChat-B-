package com.postgresql.MasChat.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.postgresql.MasChat.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    // No need to override deleteById unless custom behavior is required
}