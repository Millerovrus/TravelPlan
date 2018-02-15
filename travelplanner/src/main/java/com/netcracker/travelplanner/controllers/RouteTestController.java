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
    private ConvertPointsToListEdges convertPointsToListEdges;

    @Autowired
    private Algorithm algorithm;

    @RequestMapping(value = "/get-routes/date/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("date") String date){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return routeRepositoryService.findByStartPointAndDestinationPointAndCreationDate(from, to, parsedDate);

    }

    @RequestMapping(value = "/get-routes/", method = RequestMethod.GET)
    public List<Route> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to){

//        System.out.println("go!");
//        List<Edge> list = convertPointsToListEdges.findAll(from,to);
//
//        List<Edge> edgeList = new ArrayList<>();
//
//        List<Route> routeList = new ArrayList<>();
//
//        for (int i = 0; i < RouteType.values().length ; i++) {
//
//            List<Edge> tempEdgeList = (RoutesFinalService.separator(list,RouteType.values()[i]));
//
//            List<Edge> edges = algorithm.getMinimalRoute(tempEdgeList,"VOZ","BER");
//
//            edgeList.addAll(edges);
//
//            Route route = new Route(new Date(),from,to,RouteType.values()[i]);
//
//            int order = 1;
//            for (Edge Edges : edges) {
//
//                RouteEdge routeEdge = new RouteEdge(order++);
//                routeEdge.setRoute(route);
//                routeEdge.setEdge(Edges);
//                route.getRouteEdges().add(routeEdge);
//                route.setCost(+Edges.getCost());
//                route.setDuration(+Edges.getDuration());
//            }
//
//            routeList.add(route);
//        }
//
//        return routeList;
        return routeRepositoryService.findByStartPointAndDestinationPoint(from, to);
    }
}
