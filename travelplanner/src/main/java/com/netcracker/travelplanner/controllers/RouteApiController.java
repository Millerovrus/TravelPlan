package com.netcracker.travelplanner.controllers;

import com.google.gson.*;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.repository.EdgeRepository;
import com.netcracker.travelplanner.repository.PointRepository;
import com.netcracker.travelplanner.repository.TransitEdgesRepository;
import com.netcracker.travelplanner.security.service.SecurityService;
import com.netcracker.travelplanner.security.service.UserService;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private TransitEdgesRepository transitEdgesRepository;
    @Autowired
    private EdgeRepository edgeRepository;

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

        Gson gson = new Gson();

        Route route = null;
        Point point = null;
        Edge edge = null;
        TransitEdge transitEdge = null;

        logger.info("Процесс сохранения маршрута...");
        String email = securityService.findLoggedInUsername();
        if (email != null) {
            User user = userService.findUserByEmail(email);
            try {
                System.out.println(record);

                route = gson.fromJson(record, Route.class);
                System.out.println(route.toString());
                route.setUser(user);
                route.setCreationDate(new Date());

//                edge = gson.fromJson(record, Edge.class);
//                System.out.println(edge.toString());
//
//                transitEdge = gson.fromJson(record, TransitEdge.class);
//                System.out.println(transitEdge.toString());
//
//                point = gson.fromJson(record, Point.class);
//                System.out.println(point.toString());



                logger.info("Сохранение прошло успешно!");
            } catch (Exception ex) {
                logger.error("Процесс сохранения прерван с ошибкой: ", ex);
                ex.printStackTrace();
            }
        }
    }
}
/*    @RequestMapping(value = "/saveroutes", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveRoute(
            @RequestParam(value = "startpoint", required = true) String startPoint,
            @RequestParam(value = "destinationpoint", required = true) String destinationPoint,
            @RequestParam(value = "cost", required = true) double cost,
            @RequestParam(value = "duration", required = true) double duration,
            @RequestParam(value = "idrouteforview", required = true) int idRouteForView) {
        logger.info("Процесс сохранения маршрута...");
        String email = securityService.findLoggedInUsername();
        if (email != null) {
            User user = userService.findUserByEmail(email);
            try {
                Route route = new Route(new Date(), startPoint, destinationPoint, duration, idRouteForView);
                route.setCost(cost);
                route.setUser(user);
                routeRepositoryService.save(route);
                logger.info("Сохранение прошло успешно!");
            } catch (Exception ex) {
                logger.error("Процесс сохранения прерван с ошибкой: ", ex);
                ex.printStackTrace();
            }
        }
        //return "index";
    }*/

    /*@RequestMapping(value = "/saveroutes", method = RequestMethod.POST)
    public void saveRoute(@RequestBody String record) {
        logger.info("Процесс сохранения маршрута...");

        JsonParser parser = new JsonParser();
        JsonObject mainObject = parser.parse(record).getAsJsonObject();

        String email = securityService.findLoggedInUsername();
        if (email != null) {
            User user = userService.findUserByEmail(email);
            try {
                //Блок сохранения edges и points
                JsonArray edges = mainObject.getAsJsonArray("edges");
                for (JsonElement jedge : edges) {
                    JsonObject object = jedge.getAsJsonObject();
                    JsonObject startPoint = object.get("startPoint").getAsJsonObject();
                    Point point1 = new Point(
                            startPoint.get("name").getAsString(),
                            startPoint.get("latitude").getAsDouble(),
                            startPoint.get("longitude").getAsDouble(),
                            startPoint.get("iataCode").getAsString(),
                            startPoint.get("yandexCode").getAsString(),
                            startPoint.get("locationCode").getAsString()
                    );
                    pointRepository.save(point1);
                    JsonObject endPoint = object.get("endPoint").getAsJsonObject();
                    Point point2 = new Point(
                            endPoint.get("name").getAsString(),
                            endPoint.get("latitude").getAsDouble(),
                            endPoint.get("longitude").getAsDouble(),
                            endPoint.get("iataCode").getAsString(),
                            endPoint.get("yandexCode").getAsString(),
                            endPoint.get("locationCode").getAsString()
                    );
                    pointRepository.save(point2);
                    Edge edge = new Edge();
                    edge.setCreationDate(new Date());
                    edge.setTransportType(object.get("transportType").getAsString());!!!!!!!!!!!!

                    edge.setDuration(1.0);
                    edge.setCost(1000.0);
                    edge.setStartDate(LocalDateTime.now().plusHours(2));
                    edge.setEndDate(LocalDateTime.now());
                    edge.setCurrency("rub");
                    edge.setNumberOfTransfers(1);
                    edge.setStartPoint(point1);
                    edge.setEndPoint(point2);
                    edge.setPurchaseLink("www.kupibilet.ru");
                }
                //Конец блока сохранения points

                //Блок сохранения routes
                Route route = new Route(
                        new Date(),
                        mainObject.get("startPoint").getAsString(),
                        mainObject.get("destinationPoint").getAsString(),
                        mainObject.get("duration").getAsInt(),
                        mainObject.get("idRouteForView").getAsInt()
                );
                route.setUser(user);
                route.setCost(mainObject.get("cost").getAsInt());
                routeRepositoryService.save(route);
                //Конец блока сохранения routes



                logger.info("Сохранение прошло успешно!");
            } catch (Exception ex) {
                logger.error("Процесс сохранения прерван с ошибкой: ", ex);
                ex.printStackTrace();
            }
        }
    }*/

