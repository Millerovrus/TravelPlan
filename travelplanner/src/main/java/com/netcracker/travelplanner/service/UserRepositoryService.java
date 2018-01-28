package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryService {
    @Autowired
    private UserRepository userRepository;

    public void addUsers(List<User> list) {
        userRepository.save(list);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public Iterable<User> getUser() {
        return userRepository.findAll();
    }
}
