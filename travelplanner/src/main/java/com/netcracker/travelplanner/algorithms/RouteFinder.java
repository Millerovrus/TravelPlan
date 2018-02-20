package com.netcracker.travelplanner.algorithms;

import com.netcracker.travelplanner.entities.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Deprecated
@Service
public class RouteFinder {
    private List<Route> routes;

    public List<Route> findRoutes(List<Edge> edges, String startPoint, String destinationPoint){
        routes = new ArrayList<>();

        for (RouteType routeType:
             RouteType.values()) {
            getRoutes(edges, startPoint, destinationPoint, routeType);
        }
        return routes;
    }

    private void getRoutes(List<Edge> edges, String startPoint, String destinationPoint, RouteType routeType){
        Route route = new Route();
        Date date = new Date();

        route.setCost(0.0);
        route.setWeight(0.0);
        route.setDuration(0.0);
        route.setDistance(0.0);
        route.setStartPoint(startPoint);
        route.setDestinationPoint(destinationPoint);
        route.setRouteType(routeType);
        route.setCreationDate(date);

        //задание веса и типа для ребер
        for (Edge edge:
             edges) {
            edge.setData(routeType);
        }

        //вызов алгоритма
        Algorithm algorithm = new Algorithm();
        List<Edge> result = algorithm.getMinimalRoute(edges, startPoint, destinationPoint);

        int order = 1;

        for (Edge edge :
                result) {
            edge.setRoute(route);
            route.getEdges().add(edge);

            route.setWeight(route.getWeight() + edge.getWeight());
            if (edge.getDistance() != null){
                route.setDistance(route.getDistance() + edge.getDistance());
            }
            route.setDuration(route.getDuration() + edge.getDuration());
            route.setCost(route.getCost() + edge.getCost());
        }

        routes.add(route);
    }
}
