package com.netcracker.travelplanner.algorithms;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Алгоритм нахождения минимального пути между двумя точками полным перебором с учетом времени
 */
@Service
public class Algorithm {
    private List<Route> allFoundRoutes = new ArrayList<>();
    private List<Route> optimalFoundRoutes = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Algorithm.class);

    public List<Route> getOptimalFoundRoutes(List<Edge> edges, String startPoint, String destinationPoint) {
        edges = edges.stream().distinct().collect(Collectors.toList());
        logger.debug("Start search with {} edges", edges.size());
        startSearch(edges, startPoint, destinationPoint);
        return optimalFoundRoutes;
    }

    public List<Route> getAllFoundRoutes() {
        return allFoundRoutes;
    }

    private void startSearch(List<Edge> edges, String startPoint, String destinationPoint) {
        List<List<Edge>> allFoundEdges = new ArrayList<>();
        boolean stopSearch;

        // пробегаемся по edges, записываем все ребра, у которых startPoint == заданному startPoint
        for (Edge edge : edges) {
            if (startPoint.equals(edge.getStartPoint())) {
                List<Edge> tempEdges = new LinkedList<>();
                tempEdges.add(edge);
                allFoundEdges.add(tempEdges);
            }
        }

        byte expectedSize = 1;
        logger.debug("После прохода {}: {} найденных маршрутов", expectedSize, allFoundEdges.size());
        stopSearch = true;
        for (List<Edge> foundEdges : allFoundEdges) {
            if (!foundEdges.get(foundEdges.size()-1).getDestinationPoint().equals(destinationPoint)){
                stopSearch = false;
                break;
            }
        }
        while (!stopSearch && expectedSize < 3) {
            int size = allFoundEdges.size();
            //пробегаемся по edge и добавляем к уже найденным ребрам те, у которых startPoint соответствует найденным ранее destinationPoint и они состыкуются по времени
            for (int i = 0; i < size; i++) {
                for (Edge edge : edges) {
                    if (allFoundEdges.get(i).get(allFoundEdges.get(i).size() - 1).getDestinationPoint().equals(edge.getStartPoint()) &&
                                    timeDockingBetween(allFoundEdges.get(i).get(allFoundEdges.get(i).size() - 1), edge)){
                        List<Edge> tempEdges = new LinkedList<>();
                        tempEdges.addAll(allFoundEdges.get(i));
                        tempEdges.add(edge);
                        allFoundEdges.add(tempEdges);
                    }
                }
            }
            expectedSize++;
            /*удаляем те пути, которые нашли продолжения и уже продолжены и те,
            которые не нашли продолжения и их destinationPoint != заданному destinationPoint (они никогда не приведут к финишу)*/
            for (int i = 0; i < size; i++) {
                if ((!allFoundEdges.get(i).get(allFoundEdges.get(i).size()-1).getDestinationPoint().equals(destinationPoint) &&
                        allFoundEdges.get(i).size() != expectedSize)) {
                    allFoundEdges.remove(i);
                    i--;
                    size--;
                }
            }
            logger.debug("После прохода {}: {} найденных маршрутов", expectedSize, allFoundEdges.size());
            // если все пути имеют точку прибытия = destinationPoint - останавливаем поиск
            stopSearch = true;
            for (List<Edge> foundEdges : allFoundEdges) {
                if (!foundEdges.get(foundEdges.size()-1).getDestinationPoint().equals(destinationPoint)){
                    stopSearch = false;
                    break;
                }
            }
        }
        convertingEdgesToRoutes(allFoundEdges);
    }

    //временное решение, чтобы работала текущая программа...
    private List<Route> findOptimalRoutes(List<Route> allFoundRoutes){
        List<Route> temp = new ArrayList<>();
        double min1 = Double.POSITIVE_INFINITY, min2 = Double.POSITIVE_INFINITY, min3 = Double.POSITIVE_INFINITY;
        Route minRoute1 = allFoundRoutes.get(0), minRoute2 = allFoundRoutes.get(0), minRoute3 = allFoundRoutes.get(0);
        for (Route allFoundRoute : allFoundRoutes) {
            if (allFoundRoute.getWeights().get(0) < min1){
                minRoute1 = allFoundRoute;
                min1 = allFoundRoute.getWeights().get(0);
            } else {
                if (allFoundRoute.getWeights().get(1) < min2){
                    minRoute2 = allFoundRoute;
                    min2 = allFoundRoute.getWeights().get(1);
                } else {
                    if (allFoundRoute.getWeights().get(2) < min3){
                        minRoute3 = allFoundRoute;
                        min3 = allFoundRoute.getWeights().get(2);
                    }
                }
            }
        }
        minRoute1.setIdRouteForView(0);
        minRoute2.setIdRouteForView(1);
        minRoute3.setIdRouteForView(2);
        temp.add(minRoute1);
        temp.add(minRoute2);
        temp.add(minRoute3);
        return temp;
    }

    private void convertingEdgesToRoutes(List<List<Edge>> allFoundEdges){
        List<Route> routeList = new ArrayList<>();
        int idRouteForView  = 0;
        for (List<Edge> foundEdges : allFoundEdges) {
            Route route = new Route(new Date()
                    , foundEdges.get(0).getStartPoint()
                    , foundEdges.get(foundEdges.size() - 1).getDestinationPoint()
                    , ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size()-1).getEndDate())
                    , idRouteForView++);
            route.getWeights().add(((double) ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate())) / 72);
            route.getWeights().add(((double) ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate())) / 9);
            route.getWeights().add(((double) ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate())) / 4);
            route.getWeights().add(((double) ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate())) / 100000);
            route.getWeights().add(((double) ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate())) * 100);
            short order = 1;
            for (Edge edge : foundEdges) {
                edge.setEdgeOrder(order++);
                edge.setRoute(route);
                route.getEdges().add(edge);
                route.setCost(route.getCost() + edge.getCost());
                if (edge.getDistance() != null){
                    route.setDistance(route.getDistance() + edge.getDistance());
                }
            }
            route.getWeights().set(0, route.getWeights().get(0) + route.getCost());
            route.getWeights().set(1, route.getWeights().get(1) + route.getCost());
            route.getWeights().set(2, route.getWeights().get(2) + route.getCost());
            route.getWeights().set(3, route.getWeights().get(3) + route.getCost() * 100);
            route.getWeights().set(4, route.getWeights().get(4) + route.getCost() / 100000);
            routeList.add(route);
        }
        allFoundRoutes = routeList;
        optimalFoundRoutes = findOptimalRoutes(allFoundRoutes);
    }

    private boolean timeDockingBetween(Edge edgeFrom, Edge edgeTo) {
        return (edgeFrom.getTransportType().toLowerCase().equals("plane")
                && edgeTo.getTransportType().toLowerCase().equals("plane")
                && edgeFrom.getEndPointCode().equals(edgeTo.getStartPointCode())
                && edgeFrom.getEndDate().plusHours(2).isBefore(edgeTo.getStartDate())
                && edgeFrom.getEndDate().plusHours(10).isAfter(edgeTo.getStartDate())
                || edgeFrom.getTransportType().toLowerCase().equals("bus")
                && edgeTo.getTransportType().toLowerCase().equals("bus")
                && edgeFrom.getEndPointCode().equals(edgeTo.getStartPointCode())
                && edgeFrom.getEndDate().plusHours(1).isBefore(edgeTo.getStartDate())
                && edgeFrom.getEndDate().plusHours(9).isAfter(edgeTo.getStartDate())
                || (calculateEndDateTime(edgeFrom).isBefore(calculateStartDateTime(edgeTo))
                && (edgeFrom.getEndDate().plusHours(12).isAfter(edgeTo.getStartDate()))));
    }

    //обычно в аэропорт приезжают за 2 часа до вылета, на автобус/поезд за 30 минут, с запасом
    private LocalDateTime calculateStartDateTime(Edge edge){
        switch(edge.getTransportType().toLowerCase()){
            case "bus":
            case "train":
                return edge.getStartDate().minusMinutes(30);
            case "plane":
                return edge.getStartDate().minusHours(2);
            default:
                return edge.getStartDate().minusHours(1);
        }
    }

    //прибавляем к endDate 30 минут для поезда и автобуса, 1 час для самолета. Чтобы высадится, забрать вещи...
    //плюс 1 час на перемещение с места прибытия в место следуюшего отбытия
    private LocalDateTime calculateEndDateTime(Edge edge){
        switch(edge.getTransportType().toLowerCase()){
            case "bus":
            case "train":
                return edge.getEndDate().plusHours(1).plusMinutes(30);
            case "plane":
                return edge.getEndDate().plusHours(2);
            default:
                return edge.getEndDate().plusHours(1);
        }
    }
}
