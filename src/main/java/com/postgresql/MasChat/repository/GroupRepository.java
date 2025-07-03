package com.postgresql.MasChat.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.postgresql.MasChat.model.Group;



@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    // No need to override deleteById unless custom behavior is required
}
