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

    private final UserRepositoryService userRepositoryService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepositoryService userRepositoryService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepositoryService = userRepositoryService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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

    @Override
    public boolean checkImageURL(String URL) {
        String[] suffixes = {".jpg", ".png", ".gif", ".jpeg", ".bmp", ".webp", ".svg"};

        for (String suffix :
                suffixes) {
            if (URL.endsWith(suffix)) {
                return true;
            }
        }

        return false;
    }
}
