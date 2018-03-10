package com.netcracker.travelplanner.security.service;


import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.security.domen.Roles;
import com.netcracker.travelplanner.service.UserRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import java.util.HashSet;
import java.util.Set;

/**
 * Load user by username
 * Implements of {@link UserDetailsService} interface
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserService userService;

    @Override
    @Transactional(readOnly = true )
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid email: " + email);
        }

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        if (user.isAdmin()) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.getAuthority()));
            grantedAuthoritySet.add(new SimpleGrantedAuthority(Roles.ROLE_USER.getAuthority()));
        } else {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(Roles.ROLE_USER.getAuthority()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthoritySet);
    }
}