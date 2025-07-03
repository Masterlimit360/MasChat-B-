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

import com.postgresql.MasChat.model.Group;
import com.postgresql.MasChat.service.GroupService;



@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    // Submits a  to group the database
    @PostMapping("/save")
    public ArrayList<Group> submitGroups(@RequestBody Group body) {
        ArrayList<Group> result = groupService.submitGroupToDB(body);
        return result;
    }

    // Retrieves all Groups from the database
    @GetMapping("/getgroups")
    public ArrayList<Group> retrieveAllGroups() {
        ArrayList<Group> result = groupService.retrieveGroupsFromDB();
        return result;
    }

    // Deletes a particular group from the database
    @DeleteMapping("/delete/{id}")
    public ArrayList<Group> deleteParticularGroup(@PathVariable UUID groupid) {
        ArrayList<Group> result = groupService.deleteGroupFromDB(groupid);
        return result;
    }
}