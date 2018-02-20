package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ConvertPointsToListEdges {
    private List<Edge> resultList;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IntegrationAPIService integrationAPIService;

    class ApiRunnable implements Runnable{
        String from, to;
        LocalDate date;

        ApiRunnable(String from, String to, LocalDate date) {
            this.from = from;
            this.to = to;
            this.date = date;
        }

        @Override
        public void run() {
            List<Edge> list = integrationAPIService.getEdgesFromTo(from, to, date);
            if (!list.isEmpty()) {
                resultList.addAll(list);
            }
        }
    }

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

    List<Edge> findAll(String from, String to, LocalDate localDate){
        List<String> citiesFrom = new ArrayList<>(), citiesTo = new ArrayList<>();
        resultList = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        logger.debug("Получение ближайших городов с аэропортами в округе города " + from + " и города " + to);
        citiesFrom.addAll(integrationAPIService.getClosesCities(from));
        citiesTo.addAll(integrationAPIService.getClosesCities(to));

        List<Edge> list11 = integrationAPIService.getEdgesFromTo(from, to, localDate);
        if(!list11.isEmpty()){
            resultList.addAll(list11);
        }

        for (String aCitiesFrom : citiesFrom) {
            Runnable runnable = new ApiRunnable(from, aCitiesFrom, localDate);
            executorService.execute(runnable);
        }

        for (String aCitiesTo : citiesTo) {
            Runnable runnable = new ApiRunnable(aCitiesTo, to, localDate);
            executorService.execute(runnable);
        }

        for (String aCitiesFrom : citiesFrom) {
            for (String aCitiesTo : citiesTo) {
                Runnable runnable = new ApiRunnable(aCitiesFrom, aCitiesTo, localDate);
                executorService.execute(runnable);
            }
            Runnable runnable = new ApiRunnable(aCitiesFrom, to, localDate);
            executorService.execute(runnable);
        }

        for (String aCitiesTo: citiesTo){
            Runnable runnable = new ApiRunnable(from, aCitiesTo, localDate);
            executorService.execute(runnable);
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}