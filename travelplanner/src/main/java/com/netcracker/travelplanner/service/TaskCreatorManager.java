package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.*;
import java.util.*;

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
                ,searchInputParameters.getNumberOfPassengers()));

        if (searchInputParameters.isGlobalRoute()) {
        /* рейсы рядом с точкой отправления */
            for (Point point : searchInputParameters.getCitiesFrom()) {
                tasks.add(new Task(searchInputParameters.getFrom()
                        ,point
                        ,searchInputParameters.getDeparture()
                        ,searchInputParameters.getNumberOfPassengers()));

                /* из начальной точки в окружение конечной*/
                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture()
                        ,searchInputParameters.getNumberOfPassengers()));

                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture().plusDays(1)
                        ,searchInputParameters.getNumberOfPassengers()));
            }

        /* рядом с точкой прибытия */
            for (Point point : searchInputParameters.getCitiesTo()) {
                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture()
                        ,searchInputParameters.getNumberOfPassengers()));

                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture().plusDays(1)
                        ,searchInputParameters.getNumberOfPassengers()));

                tasks.add(new Task(point
                        ,searchInputParameters.getTo()
                        ,searchInputParameters.getDeparture().plusDays(2)
                        ,searchInputParameters.getNumberOfPassengers()));
            }

        /* перебор между всеми точками */
            for (Point pointFrom : searchInputParameters.getCitiesFrom()) {
                for (Point pointTo : searchInputParameters.getCitiesTo()) {
                    tasks.add(new Task(pointFrom
                            ,pointTo
                            ,searchInputParameters.getDeparture()
                            ,searchInputParameters.getNumberOfPassengers()));

                    tasks.add(new Task(pointFrom
                            ,pointTo
                            ,searchInputParameters.getDeparture().plusDays(1)
                            ,searchInputParameters.getNumberOfPassengers()));
                }
            }

        }
        return tasks;
    }

}
