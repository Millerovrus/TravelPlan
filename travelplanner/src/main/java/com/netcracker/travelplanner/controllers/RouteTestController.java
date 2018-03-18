package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.service.TaskManagerService;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping(value="/api/rest")
public class RouteTestController {

    @Autowired
    private RouteRepositoryService routeRepositoryService;

    @Autowired
    private TaskManagerService taskManagerService;

    private static final Logger logger = LoggerFactory.getLogger(RouteTestController.class);

    @RequestMapping(value = "/get-routes/date/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to,@RequestParam("longLatFrom") String longLatFrom, @RequestParam("longLatTo") String longLatTo, @RequestParam("date") String date){

        logger.debug("Start finding {} - {} - {}", from, to, date);

        return taskManagerService.findTheBestRoutes(from,to,longLatFrom,longLatTo,date);
    }

    @RequestMapping(value = "/get-routes/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to){
        return routeRepositoryService.findByStartPointAndDestinationPoint(from, to);
    }
}