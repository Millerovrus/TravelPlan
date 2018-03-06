package com.netcracker.travelplanner.algorithms;

import com.netcracker.travelplanner.entities.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Алгоритм нахождения минимального пути между двумя точками полным перебором
 */

@Service
public class Algorithm {
    // Список ребер с суммарным минимальным весом, т.е. искомый маршрут
    private List<Edge> minimalRoute = new LinkedList<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Метод запускает поиск маршрута и возвращает найденный маршрут
     *
     * @param edges - список ребер
     * @param startPoint - начальная точка
     * @param destinationPoint - конечная точка
     * @return minimalRoute - найденный маршрут
     */
    public List<Edge> getMinimalRoute(List<Edge> edges, String startPoint, String destinationPoint) {
//        logger.debug("Запуск алгоритма поиска " + edges.get(0).getEdgeType() + " маршрута");
        startSearch(edges, startPoint, destinationPoint);
//        logger.debug("Найденный маршрут: " + minimalRoute.toString());
        return minimalRoute;
    }

    private void startSearch(List<Edge> edges, String startPoint, String destinationPoint) {
        //двумерный список найденных путей (список ребер)
        List<List<Edge>> foundRoutes = new ArrayList<>();
        //флаг на удаление пути
        List<Boolean> needDelete = new ArrayList<>();
        // флаг остановки поиска - когда все пути дошли до destinationPoint
        boolean stopSearch = false;

        // пробегаемся по edges, записываем все ребра, у которых startPoint == заданному startPoint
        for (Edge edge : edges) {
            if (startPoint.equals(edge.getStartPoint())) {
                List<Edge> tempWay = new LinkedList<>();
                tempWay.add(edge);
                foundRoutes.add(tempWay);
                needDelete.add(false);
            }
        }

        int expectedSize = 1;
        while (!stopSearch) {
            int size = foundRoutes.size();
            //пробегаемся по edge и добавляем к уже найденным ребрам те, у которых startPoint соответствует найденным ранее destinationPoint
            for (int i = 0; i < size; i++) {
                for (Edge edge : edges) {
                    if (foundRoutes.get(i).get(foundRoutes.get(i).size() - 1).getDestinationPoint().equals(edge.getStartPoint())) {
                        List<Edge> tempWay = new LinkedList<>();
                        tempWay.addAll(foundRoutes.get(i));
                        tempWay.add(edge);
                        foundRoutes.add(tempWay);
                        needDelete.add(false);
                        needDelete.set(i, true);
                    }
                }
            }
            expectedSize++;
            //удаляем те пути, которые нашли продолжения и уже продолжены и те,
            //которые не нашли продолжения и их destinationPoint != заданному destinationPoint (они никогда не приведут к финишу)
            for (int i = 0; i < size; i++) {
                if (needDelete.get(i) || (!foundRoutes.get(i).get(foundRoutes.get(i).size()-1).getDestinationPoint().equals(destinationPoint) && foundRoutes.get(i).size() != expectedSize)) {
                    foundRoutes.remove(i);
                    needDelete.remove(i);
                    i--;
                    size--;
                }
            }

            // если все пути имеют точку прибытия = destinationPoint - останавливаем программу
            stopSearch = true;
            for (List<Edge> foundRoute : foundRoutes) {
                if (!foundRoute.get(foundRoute.size()-1).getDestinationPoint().equals(destinationPoint)){
                    stopSearch = false;
                    break;
                }
            }
        }
        // ищем путь с минимальным весом
        double minWeight = Double.POSITIVE_INFINITY;
        for (List<Edge> foundRoute : foundRoutes) {
            double weight = 0;
            for (Edge routeEdge : foundRoute) {
                weight += routeEdge.getWeight();
            }
            if (weight < minWeight){
                minimalRoute = foundRoute;
                minWeight = weight;
            } else {
                // если у путей одинаковый вес - выбираем тот, у которого меньше ребер
                if (weight == minWeight && minimalRoute.size() > foundRoute.size()){
                    minimalRoute = foundRoute;
                }
            }
        }
    }
}
