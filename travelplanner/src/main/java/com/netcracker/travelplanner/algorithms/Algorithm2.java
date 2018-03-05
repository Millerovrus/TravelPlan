package com.netcracker.travelplanner.algorithms;

import com.netcracker.travelplanner.entities.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Алгоритм нахождения минимального пути между двумя точками полным перебором
 */
@Service
public class Algorithm2 {
    private List<List<Edge>> bestFoundRoutes = new LinkedList<>();
    private List<List<Edge>> allFoundRoutes = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Algorithm2.class);

    public List<List<Edge>> getBestFoundRoutes(List<Edge> edges, String startPoint, String destinationPoint) {
        startSearch(edges, startPoint, destinationPoint);
        searchBestRoutes();
        return bestFoundRoutes;
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

    //обычно в аэропорт приезжают за 2 часа до вылета, на автобус/поезд за 1 час, с запасом
    private LocalDateTime calculateStartDateTime(Edge edge){
        switch(edge.getTransportType().toLowerCase()){
            case "bus":
            case "train":
                return edge.getStartDate().minusHours(1);
            case "plane":
                return edge.getStartDate().minusHours(2);
            default:
                return edge.getStartDate().minusHours(1);
        }
    }

    private void startSearch(List<Edge> edges, String startPoint, String destinationPoint) {
        //флаг на удаление пути
        List<Boolean> needDelete = new ArrayList<>();
        // флаг остановки поиска - когда все пути дошли до destinationPoint
        boolean stopSearch = false;

        // пробегаемся по edges, записываем все ребра, у которых startPoint == заданному startPoint
        for (Edge edge : edges) {
            if (startPoint.equals(edge.getStartPoint())) {
                List<Edge> tempWay = new LinkedList<>();
                tempWay.add(edge);
                allFoundRoutes.add(tempWay);
                needDelete.add(false);
            }
        }

        int expectedSize = 1;
        while (!stopSearch) {
            int size = allFoundRoutes.size();
            //пробегаемся по edge и добавляем к уже найденным ребрам те, у которых startPoint соответствует найденным ранее destinationPoint и они состыкаются по времени
            for (int i = 0; i < size; i++) {
                for (Edge edge : edges) {
                    if (allFoundRoutes.get(i).get(allFoundRoutes.get(i).size() - 1).getDestinationPoint().equals(edge.getStartPoint()) &&
                            calculateEndDateTime(allFoundRoutes.get(i).get(allFoundRoutes.get(i).size() - 1)).isAfter(calculateStartDateTime(edge))) {
                        List<Edge> tempWay = new LinkedList<>();
                        tempWay.addAll(allFoundRoutes.get(i));
                        tempWay.add(edge);
                        allFoundRoutes.add(tempWay);
                        needDelete.add(false);
                        needDelete.set(i, true);
                    }
                }
            }
            expectedSize++;
            //удаляем те пути, которые нашли продолжения и уже продолжены и те,
            //которые не нашли продолжения и их destinationPoint != заданному destinationPoint (они никогда не приведут к финишу)
            for (int i = 0; i < size; i++) {
                if (needDelete.get(i) || (!allFoundRoutes.get(i).get(allFoundRoutes.get(i).size()-1).getDestinationPoint().equals(destinationPoint) &&
                        allFoundRoutes.get(i).size() != expectedSize)) {
                    allFoundRoutes.remove(i);
                    needDelete.remove(i);
                    i--;
                    size--;
                }
            }

            // если все пути имеют точку прибытия = destinationPoint - останавливаем поиск
            stopSearch = true;
            for (List<Edge> foundRoute : allFoundRoutes) {
                if (!foundRoute.get(foundRoute.size()-1).getDestinationPoint().equals(destinationPoint)){
                    stopSearch = false;
                    break;
                }
            }
        }
    }
    //первый маршрут - минимальный по размеру и цене
    //второй маршрут - минимальный по размеру и длительности
    //третий маршрут - минимальный по стоимости
    //четвертый маршрут - минимальный по длительности
    private void searchBestRoutes(){
        int minSize = Integer.MAX_VALUE;
        double minCostForMinSize = Double.POSITIVE_INFINITY;
        double minDurationForMinSize = Double.POSITIVE_INFINITY;
        double minCost = Double.POSITIVE_INFINITY;
        double minDuration = Double.POSITIVE_INFINITY;
        for (List<Edge> foundRoute : allFoundRoutes) {
            double cost = 0.0;
            double duration = 0.0;
            for (Edge edge : foundRoute) {
                cost += edge.getCost();
                duration += edge.getDuration();
            }
            if (foundRoute.size() < minSize){
                if (cost < minCostForMinSize) {
                    bestFoundRoutes.set(0, foundRoute);
                    minSize = foundRoute.size();
                    minCostForMinSize = cost;
                }
                if (duration < minDurationForMinSize){
                    bestFoundRoutes.set(1, foundRoute);
                    minSize = foundRoute.size();
                    minDurationForMinSize = duration;
                }
            }
            if (cost < minCost) {
                bestFoundRoutes.set(2, foundRoute);
                minCost = cost;
            }
            if (duration < minDuration) {
                bestFoundRoutes.set(3, foundRoute);
                minDuration = duration;
            }
        }
    }
}
