package com.netcracker.travelplanner.security.service;

import com.netcracker.travelplanner.entities.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void save(User user);
}
