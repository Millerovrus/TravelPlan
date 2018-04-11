package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.security.service.SecurityService;
import com.netcracker.travelplanner.security.service.UserService;
import com.netcracker.travelplanner.service.UserRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
    Date date;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

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
     *
     * @param birthDate
     *
     * WITHOUT SPRINGSECURITY! DON'T DELETE!
     */
    @RequestMapping(value = "/changeUserData", method = RequestMethod.POST)
    //@ResponseStatus(value = HttpStatus.CREATED)
    public void changeUserData(
            @RequestParam(value = "firstname", required = true) String firstName,
            @RequestParam(value = "lastname", required = true) String lastName,
            @RequestParam(value = "birthdate", required = true) String birthDate,
            @RequestParam(value = "avatar", required = true) String avatar
            /*@RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password*/){
        logger.info("Смена данных пользователя...");
        String email = securityService.findLoggedInUsername();
        if (email != null) {
            User user = userService.findUserByEmail(email);
            try {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                Date date = new SimpleDateFormat("dd.MM.yyyy").parse(birthDate);
                user.setBirthDate(date);
                user.setAvatar(avatar);
                //Шифрование пароля
                //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userRepositoryService.save(user);
                logger.info("Изменения прошли успешно!");
            } catch (Exception ex) {
                logger.error("Процесс изменения данных прерван с ошибкой: ", ex);
                ex.printStackTrace();
            }
        }
    }
}
