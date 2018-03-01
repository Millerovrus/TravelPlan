package com.netcracker.travelplanner.security.controllers;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.security.service.SecurityService;
import com.netcracker.travelplanner.security.service.SecurityServiceImpl;
import com.netcracker.travelplanner.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("signUp"/*, new User()*/);

        return "signup.html";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String registration(@RequestParam(value = "firstname", required = true) String firstName,
                               @RequestParam(value = "lastname", required = true) String lastName,
                               @RequestParam(value = "birthdate", required = true) String birthDate,
                               @RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "password", required = true) String password) {

        logger.info("Процесс регистрации нового пользователя...");
        Date date = java.sql.Date.valueOf(birthDate);
        try {
            User user = new User(email, firstName, lastName, date, false, new Date(), password);
            //Шифрование пароля и сохрание в БД
            userService.save(user);
            logger.info("Регистрация прошла успешно!");
            //securityService.autologin(user.getEmail(), user.getPassword());
        } catch (Exception ex) {
            logger.error("Процесс регистрации прерван с ошибкой: ", ex);
            ex.printStackTrace();
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "signin.html";
    }

    /*@RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "index.html";
    }*/
}
