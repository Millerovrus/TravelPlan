package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.entities.RouteEdge;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.repository.EdgeRepository;
import com.netcracker.travelplanner.repository.RouteRepository;
import com.netcracker.travelplanner.service.ApiService;
import com.netcracker.travelplanner.service.ConvertPointsToListEdges;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import com.netcracker.travelplanner.service.RoutesFinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value="/api/rest")
public class RouteTestController {

    @Autowired
    private RouteRepositoryService routeRepositoryService;

    @Autowired
    private RoutesFinalService routesFinalService;

    @RequestMapping(value = "/get-routes/date/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("date") String date){

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date parsedDate = null;
//        try {
//            parsedDate = format.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return routeRepositoryService.findByStartPointAndDestinationPointAndCreationDate(from, to, parsedDate);

        System.out.println("start finding");
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        return routesFinalService.findTheBestRoutes(from,to,localDate);

    }

    @RequestMapping(value = "/get-routes/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to){

        return routeRepositoryService.findByStartPointAndDestinationPoint(from, to);
    }
}
