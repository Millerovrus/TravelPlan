package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.service.UserRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
    Date date;
    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @return common list of users
     */
    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    public List<User> getUsers() {
        logger.info("Запрос на получение общего списка пользователей");
        return userRepositoryService.findAll();
    }

    /**
     * @param id
     * @return user by id
     */
    @RequestMapping(value = "/findbyid", method = RequestMethod.GET)
    public User getUserById(@RequestParam(value = "id", required = true) int id) {
        logger.info("Запрос на получение пользователя с id = {}", id);
        return userRepositoryService.findById(id);
    }

    /**
     * @param lastName
     * @param firstName
     * @return user by lastname OR firstname
     */
    @RequestMapping(value = "/findbyname", method = RequestMethod.GET)
    public List<User> getUsersByLastNameIs(@RequestParam(value = "lastname", required = false) String lastName,
                                           @RequestParam(value = "firstname", required = false) String firstName) {
        logger.info("Запрос на получение пользователя по фамилии: {} или имени: {}", lastName, firstName);
        return userRepositoryService.findByLastNameOrFirstName(lastName, firstName);
    }

    /**
     * Save new user in database
     * @param firstName
     * @param lastName
     * @param email
     * @param birthDate
     * @param password
     * WITHOUT SPRINGSECURITY! DON'T DELETE!
     */
    /*@RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(@RequestParam(value = "firstname", required = true) String firstName,
                        @RequestParam(value = "lastname", required = true) String lastName,
                        @RequestParam(value = "birthdate", required = true) String birthDate,
                        @RequestParam(value = "email", required = true) String email,
                        @RequestParam(value = "password", required = true) String password){
        logger.info("Процесс регистрации нового пользователя...");
        Date date = java.sql.Date.valueOf(birthDate);
        try {
            User user = new User(email, firstName, lastName, date, false, new Date(), password);
            //Шифрование пароля
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepositoryService.save(user);
            logger.info("Регистрация прошла успешно!");
        } catch (Exception ex) {
            logger.error("Процесс регистрации прерван с ошибкой: ", ex);
            ex.printStackTrace();
        }
    }*/
}
