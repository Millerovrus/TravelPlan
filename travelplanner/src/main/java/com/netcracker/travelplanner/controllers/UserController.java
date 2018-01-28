package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserRepositoryService userRepositoryService;

    private static List<User> userList= new ArrayList<User>();

//    static {
//        userList.add(new User("hocking3@email.com", "Stephen", "Hocking", new Date(),
//                false, new Date(), "1234"));
//        userList.add(new User("korolev@yandex.com", "Sergey", "Korolev", new Date(),
//                false, new Date(), "12345"));
//    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", "Message");

        return "index";
    }

    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {

        model.addAttribute("persons", userList);

        return "personList";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        User user = new User();
        model.addAttribute("personForm", user);

        return "addPerson";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model,
                             @ModelAttribute("personForm") User user) {

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();

        if (firstName != null && firstName.length() > 0
                && lastName != null && lastName.length() > 0
                    && email != null && email.length() > 0) {
            User newPerson = new User(email, firstName,lastName, new Date(),
                    false, new Date(), "123");
            userList.add(newPerson);
            userRepositoryService.addUser(newPerson);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", "error");
        return "addPerson";
    }

    /*@GetMapping("/")
    public ModelAndView index() {
        user = new User("test3@email.com", "Mask", "Ilon", new Date(),
                false, new Date(), "123");
        userRepositoryService.addUser(user);
        Map<String, String> model = new HashMap<>();
        model.put("name", user.getLastName() + " " + user.getFirstName());
        return new ModelAndView("index", model);
    }*/
}
