package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.model.entities.User;
import com.netcracker.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryService{
    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/

    /**
     * Save user in database
     * @param user
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Save list of user in database
     * @param userList
     */
    public void save(List<User> userList) {
        userRepository.save(userList);
    }

    /**
     * @return list of all users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * @param lastName
     * @param firstName
     * @return list of users by lastname OR firstname
     */
    public List<User> findByLastNameOrFirstName(String lastName, String firstName){
        return userRepository.findAllByLastNameIsOrFirstNameIs(lastName, firstName);
    }

    /**
     * @param id
     * @return user by id
     */
    public User findById(int id){
        return userRepository.findOne(id);
    }

    /**
     * delete all model managed by repository
     */
    public void deleteAll() {
        userRepository.deleteAll();
    }

    /**
     * @param email
     * @return user by email
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
