package com.netcracker.travelplanner.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainWebController {

    @GetMapping("/")
    public String home(){
        return "index.html";
    }

    //WITHOUT SPRINGSECURITY! DON'T DELETE!
/*    @GetMapping("/signUp")
    public String signUp(){
        return "signup.html";
    }

    @GetMapping("/signIn")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "signin.html";
    }*/

    @GetMapping("/admins")
    public String admin(){
        return "admin.html";
    }

    @GetMapping("/users")
    public String users(){
        return "user.html";
    }
}
