package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteRepositoryService {
    @Autowired
    private RouteRepository routeRepository;

    public void addRoute(Route route) {
        routeRepository.save(route);
    }

    public void addRoutes(List<Route> routeList) {
        routeRepository.save(routeList);
    }

    public Iterable<Route> getRoutes() {
        return routeRepository.findAll();
    }
}
