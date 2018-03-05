package com.netcracker.travelplanner.controllers;
;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteApiController {
    private static Logger logger = LoggerFactory.getLogger(RouteApiController.class);
    @Autowired
    private RouteRepositoryService routeRepositoryService;

    /**
     * @return list of all routes
     */
    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    public List<Route> getRoutes() {
        logger.info("Запрос на получение общего списка маршрутов");
        return routeRepositoryService.findAll();
    }

    /**
     * @param id
     * @return route by id
     */
    @RequestMapping(value = "/findbyid", method = RequestMethod.GET)
    public Route getRouteById(@RequestParam(value = "id", required = true) int id) {
        logger.info("Запрос на получение маршрута с id = {}", id);
        return routeRepositoryService.findById(id);
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of routes by start AND destination point
     */
    @RequestMapping(value = "/findbytwopoints", method = RequestMethod.GET)
    public List<Route> getRoutesByTwoPoints(@RequestParam(value = "start", required = true) String s,
                                            @RequestParam(value = "destination", required = true) String d){
        logger.info("Запрос на получение маршрутов с начальной точкой: {} и конечной точкой: {}", s, d);
        return routeRepositoryService.findByStartPointAndDestinationPoint(s, d);
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of routes by start OR destination point
     */
    @RequestMapping(value = "/findbypoint", method = RequestMethod.GET)
    public List<Route> getRoutesByPoint(@RequestParam(value = "start", required = false) String s,
                                        @RequestParam(value = "destination", required = false) String d){
        logger.info("Запрос на получение маршрутов с начальной точкой: {} или конечной точкой: {}", s, d);
        return routeRepositoryService.findByStartPointOrDestinationPoint(s, d);
    }

    /**
     * @param type - type of route
     * @return list of routes by type
     */
    @RequestMapping(value = "/findbytype", method = RequestMethod.GET)
    public List<Route> getRoutesByType(@RequestParam(value = "type", required = true) RouteType type){
        logger.info("Запрос на получение маршрутов с типом маршрута: {}", type);
        return routeRepositoryService.findByRouteType(type);
    }

    /**
     * @param id - user id
     * @return list of routes by user_id
     */
    @RequestMapping(value = "/findbyuserid", method = RequestMethod.GET)
    public List<Route> getRoutesByUser(@RequestParam(value = "user", required = true) int id){
        logger.info("Запрос на получение маршрутов пользователя с id = {}", id);
        return routeRepositoryService.findByUserId(id);
    }
}
