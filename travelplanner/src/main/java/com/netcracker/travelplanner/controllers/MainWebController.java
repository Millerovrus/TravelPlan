package com.netcracker.travelplanner.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController {

    @GetMapping("/")
    public String home(){
        return "index.html";
    }
}