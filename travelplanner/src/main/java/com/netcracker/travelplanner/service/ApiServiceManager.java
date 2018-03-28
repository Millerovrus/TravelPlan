package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.api.ApiInterface;
import com.netcracker.travelplanner.entities.Point;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.SearchInputParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Deprecated
public class ApiServiceManager {

    private SearchInputParameters searchInputParameters;

    public ApiServiceManager(SearchInputParameters searchInputParameters){
        this.searchInputParameters = searchInputParameters;
    }

    public List<Callable<List<Edge>>> getTasks(ApiInterface apiInterface){

        List<Callable<List<Edge>>> callables = new ArrayList<>();

        /* прямой рейс ТОЛЬКО на выбранный день*/
        callables.add( () -> apiInterface.findEdgesFromTo(searchInputParameters.getFrom(), searchInputParameters.getTo(), searchInputParameters.getDeparture(), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren()));

        if (searchInputParameters.isGlobalRoute()) {
            for (Point point : searchInputParameters.getCitiesFrom()) {
                /* из начальной точки в окружение начальной точки ТОЛЬКО на выбранный день*/
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(searchInputParameters.getFrom(), point, searchInputParameters.getDeparture(), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                callables.add(listCallable);
                /* из окружения начальной точки в конечную точку на ВЫБРАННЫЙ день*/
                Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(point, searchInputParameters.getTo(), searchInputParameters.getDeparture(), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                callables.add(listCallable2);
                /* из окружения начальной точки в конечную точку на СЛЕДУЮЩИЙ день*/
                Callable<List<Edge>> listCallable3 = () -> apiInterface.findEdgesFromTo(point, searchInputParameters.getTo(), searchInputParameters.getDeparture().plusDays(1), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                callables.add(listCallable3);
            }

            for (Point point : searchInputParameters.getCitiesTo()) {
                /* из окружения конечной точки в конечную точку на ВЫБРАННЫЙ ДЕНЬ*/
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(point, searchInputParameters.getTo(), searchInputParameters.getDeparture(), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                callables.add(listCallable);
                /* из окружения конечной точки в конечную точку на СЛЕДУЮЩИЙ ДЕНЬ*/
                Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(point, searchInputParameters.getTo(), searchInputParameters.getDeparture().plusDays(1), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                callables.add(listCallable2);
                /* из окружения конечной точки в конечную точку на ТРЕТИЙ ДЕНЬ*/
                Callable<List<Edge>> listCallable3 = () -> apiInterface.findEdgesFromTo(point, searchInputParameters.getTo(), searchInputParameters.getDeparture().plusDays(2), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                callables.add(listCallable3);
            }

            for (Point pointFrom : searchInputParameters.getCitiesFrom()) {
                for (Point pointTo : searchInputParameters.getCitiesTo()) {
                    /* из окружения начальной точки в окружение конечной точки на ВЫБРАННЫЙ день*/
                    Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(pointFrom, pointTo, searchInputParameters.getDeparture(), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                    callables.add(listCallable);
                    /* из окружения начальной точки в окружение конечной точки на СЛЕДУЮЩИЙ день*/
                    Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(pointFrom, pointTo, searchInputParameters.getDeparture().plusDays(1), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren());
                    callables.add(listCallable2);
                }
            }
        }
        return callables;
    }
}