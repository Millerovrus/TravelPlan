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
 * Алгоритм нахождения минимального пути между двумя точками полным перебором с учетом времени
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
                    if (allFoundRoutes.get(i).get(allFoundRoutes.get(i).size() - 1).getEndIataCode().equals(edge.getStartIataCode()) &&
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

    //фильтрация всех маршрутов
    private void searchBestRoutes(){
        int minSize = Integer.MAX_VALUE;
        double minWeight1ForMinSize = Double.MAX_VALUE, minWeight2ForMinSize = Double.MAX_VALUE, minWeight3ForMinSize = Double.MAX_VALUE,
                minWeight1 = Double.MAX_VALUE, minWeight2 = Double.MAX_VALUE, minWeight3 = Double.MAX_VALUE;
        for (int i = 0; i < 6; i++) {
            bestFoundRoutes.add(null);
        }
        for (List<Edge> foundRoute : allFoundRoutes) {
            double weight1 = 0.0, weight2 = 0.0, weight3 = 0.0;
            for (Edge edge : foundRoute) {
                weight1 += edge.getCost() + edge.getDuration() / 72;
                weight2 += edge.getCost() + edge.getDuration() / 9;
                weight3 += edge.getCost() + edge.getDuration() / 2.4;
            }
            if (foundRoute.size() <= minSize){
                if (weight1 < minWeight1ForMinSize) {
                    bestFoundRoutes.set(0, foundRoute);
                    minWeight1ForMinSize = weight1;
                }
                if (weight2 < minWeight2ForMinSize){
                    bestFoundRoutes.set(1, foundRoute);
                    minWeight2ForMinSize = weight2;
                }
                if (weight3 < minWeight3ForMinSize){
                    bestFoundRoutes.set(2, foundRoute);
                    minWeight3ForMinSize = weight3;
                }
                minSize = foundRoute.size();
            }
            if (weight1 < minWeight1) {
                bestFoundRoutes.set(3, foundRoute);
                minWeight1 = weight1;
            }
            if (weight2 < minWeight2) {
                bestFoundRoutes.set(4, foundRoute);
                minWeight2 = weight2;
            }
            if (weight3 < minWeight3) {
                bestFoundRoutes.set(5, foundRoute);
                minWeight3 = weight3;
            }
        }

    }
}