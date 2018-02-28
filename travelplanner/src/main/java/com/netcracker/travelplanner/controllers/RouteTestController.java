package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.repository.*;
import com.netcracker.travelplanner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value="/api/rest")
public class RouteTestController {

    @Autowired
    private RouteRepositoryService routeRepositoryService;

    @Autowired
    private RoutesFinalService routesFinalService;

    @RequestMapping(value = "/get-routes/date/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to,@RequestParam("longLatFrom") String longLatFrom, @RequestParam("longLatTo") String longLatTo, @RequestParam("date") String date){

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date parsedDate = null;
//        try {
//            parsedDate = format.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return routeRepositoryService.findByStartPointAndDestinationPointAndCreationDate(from, to, parsedDate);

        System.out.println("start finding");
//        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);

        return routesFinalService.findTheBestRoutes(from, to, longLatFrom, longLatTo, date);
    }

    @RequestMapping(value = "/get-routes/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to){
        return routeRepositoryService.findByStartPointAndDestinationPoint(from, to);
    }
}
