package com.netcracker.travelplanner.security.services;

import com.netcracker.travelplanner.models.entities.User;
import static com.netcracker.travelplanner.security.crypto.AesCrypt.*;
import com.netcracker.travelplanner.services.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User services fro SpringSecurity
 * Implements of {@link UserService} interface
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepositoryService.findByEmail(email);
    }

    @Override
    public void save(User user) {
        user.setFirstName(encrypt(user.getFirstName()));
        user.setLastName(encrypt(user.getLastName()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepositoryService.save(user);
    }
}
