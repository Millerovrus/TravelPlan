package com.netcracker.travelplanner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.travelplanner.model.entities.Route;
import com.netcracker.travelplanner.model.entities.User;
import com.netcracker.travelplanner.security.services.SecurityService;
import com.netcracker.travelplanner.security.services.UserService;
import com.netcracker.travelplanner.services.RouteRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteApiController {
    private static Logger logger = LoggerFactory.getLogger(RouteApiController.class);
    @Autowired
    private RouteRepositoryService routeRepositoryService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;

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
                                            @RequestParam(value = "destination", required = true) String d) {
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
                                        @RequestParam(value = "destination", required = false) String d) {
        logger.info("Запрос на получение маршрутов с начальной точкой: {} или конечной точкой: {}", s, d);
        return routeRepositoryService.findByStartPointOrDestinationPoint(s, d);
    }

    /**
     * @param id - user id
     * @return list of routes by user_id
     */
    @RequestMapping(value = "/findbyuserid", method = RequestMethod.GET)
    public List<Route> getRoutesByUser(@RequestParam(value = "user", required = true) int id) {
        logger.info("Запрос на получение маршрутов пользователя с id = {}", id);
        return routeRepositoryService.findByUserId(id);
    }

    @RequestMapping(value = "/saveroutes", method = RequestMethod.POST)
    public void saveRoute(@RequestBody String record) {
        Route route;
        logger.info("Процесс сохранения маршрута...");
        String email = securityService.findLoggedInUsername();
        if (email != null) {
            User user = userService.findUserByEmail(email);
            try {
                ObjectMapper mapper = new ObjectMapper();
                route = mapper.readValue(record, Route.class);
                route.getEdges().forEach(edge -> {
                    edge.setRoute(route);
                    edge.getTransitEdgeList().forEach(transitEdge -> {
                        transitEdge.setEdge(edge);
                    });
                });
                route.setUser(user);
                routeRepositoryService.save(route);
                logger.info("Сохранение прошло успешно!");
            } catch (Exception ex) {
                logger.error("Процесс сохранения прерван с ошибкой: ", ex);
                ex.printStackTrace();
            }
        }
    }

    /**
     * Delete route by id
     * @param id
     */
    @RequestMapping(value = "/deletebyid", method = RequestMethod.GET)
    public void deleteRouteById(@RequestParam(value = "id", required = true) Integer id) {
        logger.info("Удаление маршрута с id: {}", id);
        routeRepositoryService.deleteById(id);
    }
}

