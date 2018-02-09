package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/getUsers")
public class UserApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        logger.info("Запрос на получение общего списка пользователей");
        return userRepository.findAll();
    }

    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    public User getUserById(@RequestParam(value = "id", required = true) Integer id) {
        return userRepository.findOne(id);
    }

    @RequestMapping(value = "/byname", method = RequestMethod.GET)
    public List<User> getUsersByLastNameIs(@RequestParam(value = "lastname", required = false) String lastName,
                                           @RequestParam(value = "firstname", required = false) String firstName) {
        return userRepository.findAllByLastNameIsOrFirstNameIs(lastName, firstName);
    }
}
