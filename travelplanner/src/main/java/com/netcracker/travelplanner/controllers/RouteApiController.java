package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RouteApiController {
    @Autowired
    private RouteRepositoryService routeRepositoryService;

    @GetMapping("/getRoutes")
    public Iterable<Route> getRoutes() {
        return routeRepositoryService.getRoutes();
    }
}
