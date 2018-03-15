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
public class Algorithm2 {
    private List<Route> bestFoundRoutes = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Algorithm2.class);

    public List<Route> getBestFoundRoutes(List<Edge> edges, String startPoint, String destinationPoint) {
        edges = edges.stream().distinct().collect(Collectors.toList());
        logger.debug("Start search with {} edges", edges.size());
        startSearch(edges, startPoint, destinationPoint);
        return bestFoundRoutes;
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
        searchTheBestOf(allFoundEdges);
    }

    //фильтрация всех маршрутов
    private void searchTheBestOf(List<List<Edge>> allFoundEdges){
        List<List<Edge>> bestFoundEdges = new ArrayList<>();
        logger.debug("Старт фильтрации найденных маршрутов");
        short minNumberOfTransfers = Short.MAX_VALUE;
        double minWeight1ForMinSize = Double.MAX_VALUE, minWeight2ForMinSize = Double.MAX_VALUE, minWeight3ForMinSize = Double.MAX_VALUE,
                minWeight1 = Double.MAX_VALUE, minWeight2 = Double.MAX_VALUE, minWeight3 = Double.MAX_VALUE;
        for (int i = 0; i < 6; i++) {
            bestFoundEdges.add(null);
        }
        for (List<Edge> foundEdges : allFoundEdges) {
            double weight1 = ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate()) / 72,
                    weight2 = ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate()) / 9,
                    weight3 = ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size() - 1).getEndDate()) / 2.4;
            short numberOfTransfers = 0;
            for (Edge edge : foundEdges) {
                weight1 += edge.getCost();
                weight2 += edge.getCost();
                weight3 += edge.getCost();
                numberOfTransfers += edge.getNumberOfTransfers() + 1;
            }
            if (numberOfTransfers <= minNumberOfTransfers){
                if (weight1 < minWeight1ForMinSize) {
                    bestFoundEdges.set(0, foundEdges);
                    minWeight1ForMinSize = weight1;
                }
                if (weight2 < minWeight2ForMinSize){
                    bestFoundEdges.set(1, foundEdges);
                    minWeight2ForMinSize = weight2;
                }
                if (weight3 < minWeight3ForMinSize){
                    bestFoundEdges.set(2, foundEdges);
                    minWeight3ForMinSize = weight3;
                }
                minNumberOfTransfers = numberOfTransfers;
            }
            if (weight1 < minWeight1) {
                bestFoundEdges.set(3, foundEdges);
                minWeight1 = weight1;
            }
            if (weight2 < minWeight2) {
                bestFoundEdges.set(4, foundEdges);
                minWeight2 = weight2;
            }
            if (weight3 < minWeight3) {
                bestFoundEdges.set(5, foundEdges);
                minWeight3 = weight3;
            }
        }
        bestFoundEdges = bestFoundEdges.stream().distinct().collect(Collectors.toList());
        logger.debug("Финиш фильтрации найденных маршрутов");
        convertingEdgesToRoutes(bestFoundEdges);
    }

    private void convertingEdgesToRoutes(List<List<Edge>> bestFoundEdges){
        List<Route> routeList = new ArrayList<>();

        int idRouteForView  = 0;

        for (List<Edge> foundEdges : bestFoundEdges) {
            Route route = new Route(new Date()
                    , foundEdges.get(0).getStartPoint()
                    , foundEdges.get(foundEdges.size() - 1).getDestinationPoint()
                    , ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size()-1).getEndDate())
                    , idRouteForView++);
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
            routeList.add(route);
        }
        logger.debug("Found routes:");
        for (Route route : routeList) {
            logger.debug("{}", route.toString());
        }
        bestFoundRoutes = routeList;
    }

    private boolean timeDockingBetween(Edge edgeFrom, Edge edgeTo) {
        return (edgeFrom.getTransportType().toLowerCase().equals("plane")
                && edgeTo.getTransportType().toLowerCase().equals("plane")
                && edgeFrom.getEndAirportIataCode().equals(edgeTo.getStartAirportIataCode())
                && edgeFrom.getEndDate().plusHours(2).isBefore(edgeTo.getStartDate())
                && edgeFrom.getEndDate().plusHours(10).isAfter(edgeTo.getStartDate()))
                || (calculateEndDateTime(edgeFrom).isBefore(calculateStartDateTime(edgeTo))
                && (edgeFrom.getEndDate().plusHours(12).isAfter(edgeTo.getStartDate())));
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
