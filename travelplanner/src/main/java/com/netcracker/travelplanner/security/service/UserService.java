package com.netcracker.travelplanner.security.service;

import com.netcracker.travelplanner.entities.User;

@Deprecated
public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);
}
