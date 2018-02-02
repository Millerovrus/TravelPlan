package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Deprecated
@Service
public class UserRepositoryService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void addAllUsers(List<User> userList) {
        userRepository.save(userList);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
