package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.entities.RouteEdge;
import com.netcracker.travelplanner.entities.RouteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutesFinalService {

    @Autowired
    private ConvertPointsToListEdges convertPointsToListEdges;

    @Autowired
    private Algorithm algorithm;

    private  List<Edge> separator(List<Edge> edges, RouteType type){

        List<Edge> edgeList = new ArrayList<>();
        edges.stream().filter(l->l.getEdgeType()== type).forEach(l -> {
            try {
                edgeList.add((Edge) l.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        return edgeList;
    }

    public List<Route> findTheBestRoutes(String from, String to, LocalDate localDate){

        List<Edge> list = convertPointsToListEdges.findAll(from,to,localDate);

        List<Edge> edgeList = new ArrayList<>();

        List<Route> routeList = new ArrayList<>();

        for (int i = 0; i < RouteType.values().length ; i++) {

            List<Edge> tempEdgeList = (separator(list,RouteType.values()[i]));

            List<Edge> edges = algorithm.getMinimalRoute(tempEdgeList,from,to);

            edgeList.addAll(edges);

            Route route = new Route(new Date(),from,to,RouteType.values()[i]);

            int order = 1;
            for (Edge Edges : edges) {

                RouteEdge routeEdge = new RouteEdge(order++);
                routeEdge.setRoute(route);
                routeEdge.setEdge(Edges);
                route.getRouteEdges().add(routeEdge);
                route.setCost(+Edges.getCost());
                route.setDuration(+Edges.getDuration());
            }

            routeList.add(route);
        }

        return routeList;
    }

}
