package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.api.ApiInterface;
import com.netcracker.travelplanner.entities.Point;
import com.netcracker.travelplanner.entities.Edge;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ApiServiceManager {

    private InitializatorApi initializatorApi;

    public ApiServiceManager(InitializatorApi initializatorApi){
        this.initializatorApi = initializatorApi;
    }

    public List<Callable<List<Edge>>> getTasks(ApiInterface apiInterface){

        List<Callable<List<Edge>>> callables = new ArrayList<>();

        /* прямой рейс */
        callables.add( () -> apiInterface.findEdgesFromTo(initializatorApi.getFrom(), initializatorApi.getTo(), initializatorApi.getDeparture()));

        if (initializatorApi.isGlobalRoute()) {
        /* рейсы рядом с точкой отправдения */
            for (Point point : initializatorApi.getCitiesFrom()) {
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(initializatorApi.getFrom(), point, initializatorApi.getDeparture());
                callables.add(listCallable);

                /* из начальной точки в окружение конечной*/
                Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture());
                callables.add(listCallable2);

            }

        /* рядом с точкой прибытия */
            for (Point point : initializatorApi.getCitiesTo()) {
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture());
                callables.add(listCallable);
            }

        /* перебор между всеми точками */
            for (Point pointFrom : initializatorApi.getCitiesFrom()) {

                for (Point poinTo : initializatorApi.getCitiesTo()) {
                    Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(pointFrom, poinTo, initializatorApi.getDeparture());
                    callables.add(listCallable);
                }
            }

        }

        return callables;
    }

}
