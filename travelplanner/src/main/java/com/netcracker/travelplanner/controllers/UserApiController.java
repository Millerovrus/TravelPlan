package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.model.entities.User;
import com.netcracker.travelplanner.security.services.SecurityService;
import com.netcracker.travelplanner.security.services.UserService;
import com.netcracker.travelplanner.services.UserRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import static com.netcracker.travelplanner.security.crypto.AesCrypt.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
    Date date;
    private final UserRepositoryService userRepositoryService;
    private final SecurityService securityService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Autowired
    public UserApiController(UserRepositoryService userRepositoryService, SecurityService securityService, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.userRepositoryService = userRepositoryService;
        this.securityService = securityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

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
     */
    @RequestMapping(value = "/changeUserData", method = RequestMethod.POST)
    public void changeUserData(
            @RequestParam(value = "firstname", required = true) String changeFirstName,
            @RequestParam(value = "lastname", required = true) String changeLastName,
            @RequestParam(value = "birthdate", required = true) String changeBirthDate,
            @RequestParam(value = "avatar", required = true) String changeAvatar){
        logger.info("Смена данных пользователя...");
        String email = securityService.findLoggedInUsername();
        if (email != null) {
            User user = userService.findUserByEmail(email);
            try {
                if (changeFirstName != null) {
                    user.setFirstName(encrypt(changeFirstName));
                }
                if (changeLastName != null) {
                    user.setLastName(encrypt(changeLastName));
                }
                if (changeBirthDate != null) {
                    Date date = new SimpleDateFormat("dd.MM.yyyy").parse(changeBirthDate);
                    user.setBirthDate(date);
                }
                if (userService.checkImageURL(changeAvatar)) {
                    user.setAvatar(changeAvatar);
                }

                userRepositoryService.save(user);
                logger.info("Изменения прошли успешно!");
            } catch (Exception ex) {
                logger.error("Процесс изменения данных прерван с ошибкой: ", ex);
                ex.printStackTrace();
            }
        }
    }
}
