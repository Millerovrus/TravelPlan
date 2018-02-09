package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;

import java.util.ArrayList;
import java.util.List;

public class ConvertPointsToListEdges {
    private IntegrationAPIService integrationAPIService;

    /**
     * Метод возвращает список всех рёбер, между всеми городами(аэропортами),
     * находязимися вблизи(по радиусу) города отправления и города назначения(включая эти города)
     * @param from название города отправления
     * @param to название города назначения
     * @return возврат списка рёбер
     */
    final List<Edge> findAllEdges(String from, String to) {
        List<String> stringListFrom = new ArrayList<String>();
        List<String> stringListTo = new ArrayList<String>();
        List<Edge> edges = new ArrayList<>();

        stringListFrom.add(from);
        stringListFrom.addAll(integrationAPIService.getClosesCities(from));
        stringListTo.add(to);
        stringListTo.addAll(integrationAPIService.getClosesCities(to));

        for (int i = 1; i < stringListFrom.size(); i++) {
            edges.addAll(integrationAPIService.getEdgesFromTo(stringListFrom.get(0), stringListFrom.get(i)));
        }
        for (String pointFrom : stringListFrom) {
            for (String pointTo : stringListTo) {
                edges.addAll(integrationAPIService.getEdgesFromTo(pointFrom, pointTo));
            }
        }
        for (int i = 1; i < stringListTo.size(); i++) {
            edges.addAll(integrationAPIService.getEdgesFromTo(stringListTo.get(i), stringListTo.get(0)));
        }
        return edges;
    }
}

