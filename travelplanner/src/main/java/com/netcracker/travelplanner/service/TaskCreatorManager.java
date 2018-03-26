package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Point;
import com.netcracker.travelplanner.entities.SearchInputParameters;
import com.netcracker.travelplanner.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskCreatorManager {

    private SearchInputParameters searchInputParameters;

    public TaskCreatorManager(SearchInputParameters searchInputParameters){
        this.searchInputParameters = searchInputParameters;
    }

    public List<Task> getTasks(){

        List<Task> tasks = new ArrayList<>();

        /* прямой рейс */

        tasks.add(new Task(searchInputParameters.getFrom()
                ,searchInputParameters.getTo()
                ,searchInputParameters.getDeparture()
                ,searchInputParameters.getNumberOfAdults()
                ,searchInputParameters.getNumberOfChildren()));

        if (searchInputParameters.isGlobalRoute()) {
        /* рейсы рядом с точкой отправления */
            for (Point point : searchInputParameters.getCitiesFrom()) {
                tasks.add(new Task(searchInputParameters.getFrom()
                        ,point
                        ,searchInputParameters.getDeparture()
                        ,searchInputParameters.getNumberOfAdults()
                        ,searchInputParameters.getNumberOfChildren()));

                /* из начальной точки в окружение конечной*/
                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture()
                        ,searchInputParameters.getNumberOfAdults()
                        ,searchInputParameters.getNumberOfChildren()));

                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture().plusDays(1)
                        ,searchInputParameters.getNumberOfAdults()
                        ,searchInputParameters.getNumberOfChildren()));
            }

        /* рядом с точкой прибытия */
            for (Point point : searchInputParameters.getCitiesTo()) {
                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture()
                        ,searchInputParameters.getNumberOfAdults()
                        ,searchInputParameters.getNumberOfChildren()));

                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture().plusDays(1)
                        ,searchInputParameters.getNumberOfAdults()
                        ,searchInputParameters.getNumberOfChildren()));

                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture().plusDays(2)
                        ,searchInputParameters.getNumberOfAdults()
                        ,searchInputParameters.getNumberOfChildren()));
            }

        /* перебор между всеми точками */
            for (Point pointFrom : searchInputParameters.getCitiesFrom()) {
                for (Point pointTo : searchInputParameters.getCitiesTo()) {
                    tasks.add(new Task(pointFrom
                            ,pointTo
                            ,searchInputParameters.getDeparture()
                            ,searchInputParameters.getNumberOfAdults()
                            ,searchInputParameters.getNumberOfChildren()));

                    tasks.add(new Task(pointFrom
                            ,pointTo
                            ,searchInputParameters.getDeparture().plusDays(1)
                            ,searchInputParameters.getNumberOfAdults()
                            ,searchInputParameters.getNumberOfChildren()));
                }
            }

        }
        return tasks;
    }

}
