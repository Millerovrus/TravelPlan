package com.netcracker.travelplanner.algorithms;

import com.netcracker.travelplanner.entities.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Алгоритм нахождения минимального пути между двумя точками полным перебором с учетом времени
 */
@Service
public class Algorithm {
    private List<Route> optimalFoundRoutes = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Algorithm.class);
    private final AlgorithmProperties properties;

    @Autowired
    public Algorithm(AlgorithmProperties properties) {
        this.properties = properties;
    }

    public List<Route> getOptimalFoundRoutes(List<Edge> edges, String startPoint, String destinationPoint, int numberOfPassengers) {
        if (edges.isEmpty()){
            logger.debug("Edge list is empty =(");
            return null;
        }
        edges = edges.stream().distinct().collect(Collectors.toList());
        logger.debug("Start search with {} edges", edges.size());
        startSearch(edges, startPoint, destinationPoint, numberOfPassengers);
        return optimalFoundRoutes;
    }

    private void startSearch(List<Edge> edges, String startPoint, String destinationPoint, int numberOfPassengers) {
        List<List<Edge>> allFoundEdges = new ArrayList<>();

        // пробегаемся по edges, записываем все ребра, у которых startPoint == заданному startPoint
        for (Edge edge : edges) {
            if (startPoint.equals(edge.getStartPoint().getName())) {
                List<Edge> tempEdges = new LinkedList<>();
                tempEdges.add(edge);
                allFoundEdges.add(tempEdges);
            }
        }

        byte expectedSize = 1;
        logger.debug("После прохода {}: {} найденных маршрутов", expectedSize, allFoundEdges.size());
        boolean stopSearch = true;
        for (List<Edge> foundEdges : allFoundEdges) {
            if (!foundEdges.get(foundEdges.size()-1).getEndPoint().getName().equals(destinationPoint)){
                stopSearch = false;
                break;
            }
        }
        while (!stopSearch && expectedSize < 3) {
            int size = allFoundEdges.size();
            //пробегаемся по edge и добавляем к уже найденным ребрам те, у которых startPoint соответствует найденным ранее destinationPoint и они состыкуются по времени
            for (int i = 0; i < size; i++) {
                for (Edge edge : edges) {
                    if (allFoundEdges.get(i).get(allFoundEdges.get(i).size() - 1).getEndPoint().getName().equals(edge.getStartPoint().getName()) &&
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
                if ((!allFoundEdges.get(i).get(allFoundEdges.get(i).size()-1).getEndPoint().getName().equals(destinationPoint) &&
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
                if (!foundEdges.get(foundEdges.size()-1).getEndPoint().getName().equals(destinationPoint)){
                    stopSearch = false;
                    break;
                }
            }
        }
        convertingEdgesToRoutes(allFoundEdges, numberOfPassengers);
    }

    private void findOptimalRoutes(List<Route> allFoundRoutes){
        int l = properties.getRoutesByCoefCount();
        if (allFoundRoutes.size() < properties.getRoutesByCoefCount()){
            l = allFoundRoutes.size();
        }

        Route[][] minRoutes = new Route[properties.getCoefsCount()][l];
        int[] counters = {0, 0, 0, 0, 0};

        for (Route eachFoundRoute : allFoundRoutes) {
            for (int i = 0; i < properties.getCoefsCount(); i++){
                for (int j = 0; j < l; j++){
                    if (minRoutes[i][j] == null){
                        minRoutes[i][j] = eachFoundRoute;
                        counters[i]++;
                        break;
                    }
                    if (eachFoundRoute.getWeights().get(i) < minRoutes[i][j].getWeights().get(i)){
                        if (counters[i] < l){
                            System.arraycopy(minRoutes[i], j, minRoutes[i], j + 1, counters[i]++ - j);
                        }
                        else
                        {
                            System.arraycopy(minRoutes[i], j, minRoutes[i], j + 1, counters[i] - j - 1);
                        }
                        minRoutes[i][j] = eachFoundRoute;
                        break;
                    }
                }
            }
        }

        optimalFoundRoutes.clear();

        for (int i = 0; i < properties.getCoefsCount(); i++){
            for (int j = 0; j < l; j++){
                if (!optimalFoundRoutes.contains(minRoutes[i][j])){
                    optimalFoundRoutes.add(minRoutes[i][j]);
                }
            }
        }

        for (Route route : optimalFoundRoutes) {
            for (int i = 0; i < properties.getCoefsCount(); i++) {
                if (minRoutes[i][0].equals(route)){
                    route.setOptimalRoute(true);
                    break;
                }
            }
        }

        setDescriptions(optimalFoundRoutes);
    }

    private void convertingEdgesToRoutes(List<List<Edge>> allFoundEdges, int numberOfPassengers){
        List<Route> routeList = new ArrayList<>();
        int idRouteForView  = 0;
        for (List<Edge> foundEdges : allFoundEdges) {
            Route route = new Route(new Date()
                    , foundEdges.get(0).getStartPoint().getName()
                    , foundEdges.get(foundEdges.size() - 1).getEndPoint().getName()
                    , ChronoUnit.SECONDS.between(foundEdges.get(0).getStartDate(), foundEdges.get(foundEdges.size()-1).getEndDate())
                    , idRouteForView++);
            short order = 1;
            for (Edge edge : foundEdges) {
                edge.setEdgeOrder(order++);
                edge.setRoute(route);
                route.getEdges().add(edge);
                route.setCost(route.getCost() + edge.getCost());
            }
            route.getWeights().add(route.getDuration() / 72 + route.getCost() / numberOfPassengers);
            route.getWeights().add(route.getDuration() / 9 + route.getCost() / numberOfPassengers);
            route.getWeights().add(route.getDuration() / 4 + route.getCost() / numberOfPassengers);
            route.getWeights().add(route.getDuration() / 100000 + route.getCost() * 10 / numberOfPassengers);
            route.getWeights().add(route.getDuration() + route.getCost() / 100000 / numberOfPassengers);

            routeList.add(route);
        }
        findOptimalRoutes(routeList);
    }

    private boolean timeDockingBetween(Edge edgeFrom, Edge edgeTo) {
        return (
                //Если прилет и вылет с одного аэропорта, то состыковка в 2 часа минимум
                !edgeFrom.getEndPoint().getLocationCode().isEmpty()
                && !edgeTo.getStartPoint().getLocationCode().isEmpty()
                && edgeFrom.getTransportType().toLowerCase().equals("plane")
                && edgeTo.getTransportType().toLowerCase().equals("plane")
                && edgeFrom.getEndPoint().getLocationCode().equals(edgeTo.getStartPoint().getLocationCode())
                && edgeFrom.getEndDate().plusHours(2).isBefore(edgeTo.getStartDate())
                && edgeFrom.getEndDate().plusHours(10).isAfter(edgeTo.getStartDate())

                //Если приезд и выезд с одной остановки, то состыковка в 30 минут минимум
                || !edgeFrom.getEndPoint().getLocationCode().isEmpty()
                && !edgeTo.getStartPoint().getLocationCode().isEmpty()
                && edgeFrom.getTransportType().toLowerCase().equals("bus")
                && edgeTo.getTransportType().toLowerCase().equals("bus")
                && edgeFrom.getEndPoint().getLocationCode().equals(edgeTo.getStartPoint().getLocationCode())
                && edgeFrom.getEndDate().plusMinutes(30).isBefore(edgeTo.getStartDate())
                && edgeFrom.getEndDate().plusHours(9).isAfter(edgeTo.getStartDate())

                //В остальном если состыкуются по времени, то пропускаем, если нет, то нет
                || (calculateEndDateTime(edgeFrom).isBefore(calculateStartDateTime(edgeTo))
                && (edgeFrom.getEndDate().plusHours(12).isAfter(edgeTo.getStartDate()))));
    }

    //обычно в аэропорт приезжают за 2 часа до вылета, на автобус/поезд за 30 минут, с запасом
    private LocalDateTime calculateStartDateTime(Edge edge){
        switch(edge.getTransportType().toLowerCase()){
            case "bus":
            case "train":
                return edge.getStartDate().minusMinutes(properties.getBusPauseMinutes());
            case "plane":
                return edge.getStartDate().minusHours(properties.getPlanePauseHours());
            default:
                return edge.getStartDate().minusHours(properties.getDefaultPauseHours());
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

    private void setDescriptions(List<Route> routes){
        double minCost = Double.MAX_VALUE, minDuration = Double.MAX_VALUE;
        int minCostInd = 0, minDurInd = 0;

        for (int i = 0; i < routes.size(); i++){
            if (routes.get(i).getCost() < minCost){
                minCost = routes.get(i).getCost();
                minCostInd = i;
            }

            if (routes.get(i).getDuration() < minDuration){
                minDuration = routes.get(i).getDuration();
                minDurInd = i;
            }
        }

        if (minCostInd == minDurInd){
            routes.get(minCostInd).setDescription("Cheapest & fastest!");
        }
        else{
            routes.get(minCostInd).setDescription("Cheapest!");
            routes.get(minDurInd).setDescription("Fastest!");
        }
    }
}