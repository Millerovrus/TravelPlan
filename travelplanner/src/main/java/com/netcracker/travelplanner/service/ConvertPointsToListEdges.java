package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertPointsToListEdges {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IntegrationAPIService integrationAPIService;

    /**
     * Метод возвращает список всех рёбер, между всеми городами(аэропортами),
     * находязимися вблизи(по радиусу) города отправления и города назначения(включая эти города)
     * @param from название города отправления
     * @param to название города назначения
     * @return возврат списка рёбер
     */
    @Deprecated
    public List<Edge> findAllEdges(String from, String to, LocalDate localDate) {
        List<String> stringListFrom = new ArrayList<>();
        List<String> stringListTo = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        stringListFrom.add(from);
        stringListFrom.addAll(integrationAPIService.getClosesCities(from));
        stringListTo.add(to);
        stringListTo.addAll(integrationAPIService.getClosesCities(to));

        for (int i = 1; i < stringListFrom.size(); i++) {

            edges.addAll(integrationAPIService.getEdgesFromTo(from, stringListFrom.get(i), localDate));
        }
        for (String pointFrom : stringListFrom) {
            for (String pointTo : stringListTo) {
                edges.addAll(integrationAPIService.getEdgesFromTo(pointFrom, pointTo, localDate));
            }
        }
        for (int i = 1; i < stringListTo.size(); i++) {
            edges.addAll(integrationAPIService.getEdgesFromTo(stringListTo.get(i), stringListTo.get(0), localDate));
        }
        return edges;
    }

    public List<Edge> findAll(String from, String to, LocalDate localDate){
        List<String> citiesFrom = new ArrayList<>();

        logger.debug("Получение ближайших городов с аэропортами в округе города " + from + " и города " + to);
        citiesFrom.addAll(integrationAPIService.getClosesCities(from));

        List<String> citiesTo = new ArrayList<>();

        citiesTo.addAll(integrationAPIService.getClosesCities(to));

        List<Edge> resultList = new ArrayList<>();

        List<Edge> list11 = integrationAPIService.getEdgesFromTo(from, to, localDate);
        if(!list11.isEmpty()){
            resultList.addAll(list11);
        }


        for (String aCitiesFrom : citiesFrom) {
            List<Edge> list = integrationAPIService.getEdgesFromTo(from, aCitiesFrom, localDate);
            if (!list.isEmpty()) {
                resultList.addAll(list);
            }
        }

        for (String aCitiesTo : citiesTo) {
            List<Edge> list = integrationAPIService.getEdgesFromTo(aCitiesTo, to, localDate);
            if (!list.isEmpty()) {
                resultList.addAll(list);
            }
        }

        for (String aCitiesFrom : citiesFrom) {
            List<Edge> list1 = new ArrayList<>();
            for (String aCitiesTo : citiesTo) {
                List<Edge> list = integrationAPIService.getEdgesFromTo(aCitiesFrom, aCitiesTo, localDate);
                if (!list.isEmpty()) {
                    list1.addAll(list);
                }
            }
            List<Edge> list = integrationAPIService.getEdgesFromTo(aCitiesFrom, to, localDate);
            if (!list.isEmpty()){
                resultList.addAll(list);
            }
            resultList.addAll(list1);
        }

        for (String aCitiesTo: citiesTo){
            List<Edge> list = integrationAPIService.getEdgesFromTo(from, aCitiesTo, localDate);
            if (!list.isEmpty()){
                resultList.addAll(list);
            }
        }

        return resultList;
    }
}

