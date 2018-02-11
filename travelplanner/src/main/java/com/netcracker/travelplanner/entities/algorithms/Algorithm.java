package com.netcracker.travelplanner.entities.algorithms;

import com.netcracker.travelplanner.entities.Edge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Алгоритм нахождения минимального пути между двумя точками полным перебором
 */
public class Algorithm {
    //двумерный список найденных путей (список ребер)
    private List<List<Edge>> ways = new ArrayList<>();
    //флаг на удаление пути
    private List<Boolean> needDelete = new ArrayList<>();
    // Список ребер с суммарным минимальным весом, т.е. искомый маршрут
    private List<Edge> route = new LinkedList<>();

    /**
     * Метод запускает поиск маршрута и возвращает найденный маргрут
     *
     * @param edges - список ребер
     * @param startPoint - начальная точка
     * @param destinationPoint - конечная точка
     * @return route - найденный маршрут
     */
    public List<Edge> getRoute(List<Edge> edges, String startPoint, String destinationPoint) {
        startSearch(edges, startPoint, destinationPoint);
        return route;
    }

    private void startSearch(List<Edge> edges, String startPoint, String destinationPoint) {
        // флаг остановки поиска - когда все пути дошли до destinationPoint
        boolean stopSearch = false;

        // пробегаемся по edges, записываем все ребра, у которых startPoint == заданному startPoint
        for (Edge edge : edges) {
            if (startPoint.equals(edge.getStartPoint())) {
                List<Edge> tempWay = new LinkedList<>();
                tempWay.add(edge);
                ways.add(tempWay);
                needDelete.add(false);
            }
        }
        while (!stopSearch) {
            int size = ways.size();
            //пробегаемся по edge и добавляем к уже найденным ребрам те, у которых startPoint соответствует найденным ранее destinationPoint
            for (int i = 0; i < size; i++) {
                for (Edge edge : edges) {
                    if (ways.get(i).get(ways.get(i).size() - 1).getDestinationPoint().equals(edge.getStartPoint())) {
                        List<Edge> tempWay = new LinkedList<>();
                        tempWay.addAll(ways.get(i));
                        tempWay.add(edge);
                        ways.add(tempWay);
                        needDelete.add(false);
                        needDelete.set(i, true);
                    }
                }
            }
            //удаляем те пути, которые нашли продолжение
            for (int i = 0; i < size; i++) {
                if (needDelete.get(i)) {
                    ways.remove(i);
                    needDelete.remove(i);
                    i--;
                    size--;
                }
            }
            // если все пути имеют точку прибытия = destinationPoint - останавливаем программу
            stopSearch = true;
            for (List<Edge> way : ways) {
                if (!way.get(way.size()-1).getDestinationPoint().equals(destinationPoint)){
                    stopSearch = false;
                    break;
                }
            }
        }
        // ищем путь с минимальным весом
        double minWeight = Double.POSITIVE_INFINITY;
        for (List<Edge> way : ways) {
            int weight = 0;
            for (Edge e : way) {
                weight += e.getWeight();
            }
            if (weight < minWeight){
                route = way;
                minWeight = weight;
            }
            // если у путей одинаковый вес - выбираем тот, у которого меньше ребер
            if (weight == minWeight && route.size() > way.size()){
                route = way;
            }
        }
    }
}
