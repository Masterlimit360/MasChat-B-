package com.postgresql.MasChat.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postgresql.MasChat.model.Group;
import com.postgresql.MasChat.repository.GroupRepository;



@Service
public class GroupService {
  @Autowired
    private GroupRepository groupRepository;

    // Submits a group to the database
    public ArrayList<Group> submitGroupToDB(Group groupData) {
        groupRepository.save(groupData);
        ArrayList<Group> result = retrieveGroupsFromDB();
        return result;
    }

    public ArrayList<Group> retrieveGroupsFromDB() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveMessagesFromDB'");
    }

    // Retrieves all groups from the database
    public ArrayList<Group> retrievegroupsFromDB() {
        ArrayList<Group> result = (ArrayList<Group>) groupRepository.findAll();
        return result;
    }

    // Deletes a particular group from the database
    public ArrayList<Group> deleteGroupFromDB(UUID groupid) {
        groupRepository.deleteById(groupid);
        ArrayList<Group> result = retrieveGroupsFromDB();
        return result;
    }
   }