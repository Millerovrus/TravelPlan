package com.netcracker.travelplanner.algorithms;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.entities.RouteEdge;
import com.netcracker.travelplanner.entities.RouteType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            RouteEdge routeEdge = new RouteEdge(order++);
            routeEdge.setEdge(edge);
            routeEdge.setRoute(route);
            route.getRouteEdges().add(routeEdge);

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
