package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserRepositoryService userRepositoryService;

    @GetMapping("/user")
    public Iterable<User> getUser() {
        return userRepositoryService.getUser();
    }
}
