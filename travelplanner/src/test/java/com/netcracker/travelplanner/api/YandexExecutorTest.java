package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class YandexExecutorTest {




    @Test
    public void execute() throws Exception {

        YandexApi yandexApi = new YandexApi();

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-17");



        List<Callable<List<Edge>>> callables = new ArrayList<>();

        callables.add( () -> yandexApi.findEdgesFromTo(initializatorApi.getFrom(), initializatorApi.getTo(), initializatorApi.getDeparture()) );

        for (Point point : initializatorApi.getCitiesFrom()) {
            Callable<List<Edge>> listCallable = () -> yandexApi.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture());
            callables.add(listCallable);
        }

        for (Point point : initializatorApi.getCitiesTo()) {
            Callable<List<Edge>> listCallable = () -> yandexApi.findEdgesFromTo(initializatorApi.getFrom(), point, initializatorApi.getDeparture());
            callables.add(listCallable);
        }



        YandexExecutor yandexExecutor = new YandexExecutor();

        List<Edge> edgeList = yandexExecutor.execute(callables);

        edgeList.forEach(edge -> System.out.println(edge.toString()));

    }

}