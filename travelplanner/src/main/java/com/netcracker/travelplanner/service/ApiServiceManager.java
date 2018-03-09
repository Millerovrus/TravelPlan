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

        /* прямой рейс ТОЛЬКО на выбранный день*/
        callables.add( () -> apiInterface.findEdgesFromTo(initializatorApi.getFrom(), initializatorApi.getTo(), initializatorApi.getDeparture()));

        if (initializatorApi.isGlobalRoute()) {
            for (Point point : initializatorApi.getCitiesFrom()) {
                /* из начальной точки в окружение начальной точки ТОЛЬКО на выбранный день*/
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(initializatorApi.getFrom(), point, initializatorApi.getDeparture());
                callables.add(listCallable);
                /* из окружения начальной точки в конечную точку на ВЫБРАННЫЙ день*/
                Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture());
                callables.add(listCallable2);
                /* из окружения начальной точки в конечную точку на СЛЕДУЮЩИЙ день*/
                Callable<List<Edge>> listCallable3 = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture().plusDays(1));
                callables.add(listCallable3);
            }

            for (Point point : initializatorApi.getCitiesTo()) {
                /* из окружения конечной точки в конечную точку на ВЫБРАННЫЙ ДЕНЬ*/
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture());
                callables.add(listCallable);
                /* из окружения конечной точки в конечную точку на СЛЕДУЮЩИЙ ДЕНЬ*/
                Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture().plusDays(1));
                callables.add(listCallable2);
                /* из окружения конечной точки в конечную точку на ТРЕТИЙ ДЕНЬ*/
                Callable<List<Edge>> listCallable3 = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture().plusDays(2));
                callables.add(listCallable3);
                /* из окружения конечной точки в конечную точку на ЧЕТВЕРТЫЙ ДЕНЬ*/
                Callable<List<Edge>> listCallable4 = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture().plusDays(3));
                callables.add(listCallable4);
            }

            for (Point pointFrom : initializatorApi.getCitiesFrom()) {
                for (Point pointTo : initializatorApi.getCitiesTo()) {
                    /* из окружения начальной точки в окружение конечной точки на ВЫБРАННЫЙ день*/
                    Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(pointFrom, pointTo, initializatorApi.getDeparture());
                    callables.add(listCallable);
                    /* из окружения начальной точки в окружение конечной точки на СЛЕДУЮЩИЙ день*/
                    Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(pointFrom, pointTo, initializatorApi.getDeparture().plusDays(1));
                    callables.add(listCallable2);
                }
            }
        }
        return callables;
    }
}
