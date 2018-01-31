package com.netcracker.travelplanner.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RouteApiController {
    @Autowired
    private RouteRepositoryService routeRepositoryService;

    @GetMapping("/getRoutes")
    public List<Route> getRoutes() {
        return routeRepositoryService.getAllRoutes();
    }
}
