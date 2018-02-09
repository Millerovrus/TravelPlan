package com.netcracker.travelplanner.controllers;
;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/getRoutes")
public class RouteApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    public List<Route> getRoutes() {
        logger.info("Запрос на получение общего списка маршрутов");
        return routeRepository.findAll();
    }

    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    public Route getRouteById(@RequestParam(value = "id", required = true) Integer id) {
        return routeRepository.findOne(id);
    }

    @RequestMapping(value = "/bytwopoints", method = RequestMethod.GET)
    public List<Route> getRoutesByTwoPoints(@RequestParam(value = "start", required = true) String s,
                                            @RequestParam(value = "destination", required = true) String d){
        return routeRepository.findByStartPointIsAndDestinationPointIs(s, d);
    }

    @RequestMapping(value = "/bypoint", method = RequestMethod.GET)
    public List<Route> getRoutesByPoint(@RequestParam(value = "start", required = false) String s,
                                        @RequestParam(value = "destination", required = false) String d){
        return routeRepository.findByStartPointIsOrDestinationPointIs(s, d);
    }

    @RequestMapping(value = "/bytype", method = RequestMethod.GET)
    public List<Route> getRoutesByType(@RequestParam(value = "type", required = true) Integer i){
        return routeRepository.findByRouteTypeIs(i);
    }

    @RequestMapping(value = "/byuser", method = RequestMethod.GET)
    public List<Route> getRoutesByUser(@RequestParam(value = "user", required = true) Integer i){
        return routeRepository.findByUserId(i);
    }
}
