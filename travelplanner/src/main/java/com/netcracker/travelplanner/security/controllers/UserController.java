package com.netcracker.travelplanner.security.controllers;

import com.netcracker.travelplanner.models.entities.User;
import com.netcracker.travelplanner.security.services.SecurityService;
import com.netcracker.travelplanner.security.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private SecurityService securityService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String registration() {
        return "signup";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String registration( Model model,
                                @RequestParam(value = "firstname", required = true) String firstName,
                                @RequestParam(value = "lastname", required = true) String lastName,
                                @RequestParam(value = "birthdate", required = true) String birthDate,
                                @RequestParam(value = "email", required = true) String email,
                                @RequestParam(value = "password", required = true) String password) {

        logger.info("Process of registration new user...");
        Date date = java.sql.Date.valueOf(birthDate);
        User user = new User(email, firstName, lastName, date, false, new Date(), password);
        model.addAttribute("email", userService.findUserByEmail(email) != null);
        try {
            if (userService.findUserByEmail(email) == null) {
                user.setAvatar("img/man-user.png");
                //Шифрование пароля и сохрание в БД
                userService.save(user);
                logger.info("Registration is successful!");
                securityService.autologin(email, password);
                return "redirect:/users";
            } else {
                logger.error("Error! Email already exists: {}", email);
                return "signup";
            }
        } catch (Exception ex) {
            logger.error("The registration process was interrupted with an error: " + ex);
            ex.printStackTrace();
            return "signup";
        }
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "signin";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model){

        String email = securityService.findLoggedInUsername();
        //model.addAttribute("email", email);

        if (email != null) {
            User user = userService.findUserByEmail(email);
            model.addAttribute("firstname", user.getFirstName());
            model.addAttribute("lastname", user.getLastName());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("birthdate", user.getBirthDate());
            model.addAttribute("avatar", user.getAvatar());
            model.addAttribute("user_id", user.getId());
        }
        model.addAttribute("isAuthorized", email != null);
        return "user";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model){
        String email = securityService.findLoggedInUsername();
        if (email != null) {
            User user = userService.findUserByEmail(email);
            //model.addAttribute("user_id", user.getId());
            model.addAttribute("firstname", user.getFirstName());
            model.addAttribute("lastname", user.getLastName());
        }
        model.addAttribute("isAuthorized", email != null);
        return "index";
    }
}
