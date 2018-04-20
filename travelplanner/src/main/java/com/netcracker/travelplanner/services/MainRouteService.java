package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.api.*;
import com.netcracker.travelplanner.model.*;
import com.netcracker.travelplanner.executors.Executor;
import com.netcracker.travelplanner.model.entities.Edge;
import com.netcracker.travelplanner.model.entities.IntegrationError;
import com.netcracker.travelplanner.model.entities.Route;
import com.netcracker.travelplanner.model.exceptions.KiwiIATACodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;

@Service
public class MainRouteService {

    @Autowired
    private  Algorithm algorithm;

    @Autowired
    private ErrorRepositoryService errorRepositoryService;

    @Value("${route-service.threads-count}")
    private Integer threadsCount;

    @Value("${route-service.timeout-minutes}")
    private Integer timeout;

    private final Logger logger = LoggerFactory.getLogger(MainRouteService.class);

    private KiwiApi kiwiApi = new KiwiApi();
    private YandexApi yandexApi = new YandexApi();
    private UFSParser UFSParser = new UFSParser();

    private Executor executor1 = new Executor();
    private Executor executor2 = new Executor();
    private Executor executor3 = new Executor();

    private SearchInputParameters prepareInputData(String from
            , String to
            , String latLongFrom
            , String latLongTo
            , String date
            , int numberOfAdults
            , int numberOfChildren
            , int numberOfInfants){

        SearchInputParameters parameters = null;
        try {
            parameters = new PreparingDataService().prepareData(from
                    ,to
                    ,latLongFrom
                    ,latLongTo
                    ,date
                    ,numberOfAdults
                    ,numberOfChildren
                    ,numberOfInfants);
        } catch (KiwiIATACodeException e) {
            String description = e.getMessage();
            if (e.getCause() != null){
                description += e.getCause().getMessage();
            }
            errorRepositoryService.saveError(new IntegrationError(description, new Date(), "EdgeService"));
        }
        return parameters;
    }

    private List<Task> getTasks(SearchInputParameters searchInputParameters){
        return new TaskCreatorManager(searchInputParameters).getTasks();
    }

    private List<Edge> getAllEdges(ExecutorService executorService, List<Task> taskList){

        executor1.setErrorRepositoryService(errorRepositoryService);
        executor2.setErrorRepositoryService(errorRepositoryService);
        executor3.setErrorRepositoryService(errorRepositoryService);

        List<Edge> edgeList  = Collections.synchronizedList(new ArrayList<>());

        executorService.execute( () ->{
            logger.debug("Start Thread yandexApi");
            edgeList.addAll(executor1.execute(taskList,yandexApi));
        });


        executorService.execute( () -> {
            logger.debug("Start Thread kiwiApi");
            edgeList.addAll(executor2.execute(taskList,kiwiApi));
        });


        executorService.execute( () -> {
            logger.debug("Start Thread UFSParser");
            edgeList.addAll(executor3.execute(taskList,UFSParser));
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(6, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            errorRepositoryService.saveError(new IntegrationError(e.getMessage(), new Date(), "ExecutorService"));
        }

        return edgeList;
    }

    private List<Route> getRoutes(List<Edge> edgeList, String startPoint, String endPoint, int numberOfPassengers){
        algorithm.setErrorRepositoryService(errorRepositoryService);
        return algorithm.getOptimalFoundRoutes(edgeList,startPoint,endPoint,numberOfPassengers);
    }

    public List<Route> findBestRoutes(String from
            ,String to
            ,String latLongFrom
            ,String latLongTo
            ,String date
            ,int numberOfAdults
            ,int numberOfChildren
            ,int numberOfInfants
            )
    {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

        SearchInputParameters searchInputParameters = prepareInputData(from
                ,to
                ,latLongFrom
                ,latLongTo
                ,date
                ,numberOfAdults
                ,numberOfChildren
                ,numberOfInfants);

        List<Task> taskList = getTasks(searchInputParameters);

        List<Edge> edgeList = getAllEdges(executorService,taskList);

        return getRoutes(edgeList
                ,searchInputParameters.getFrom().getName()
                ,searchInputParameters.getTo().getName()
                ,searchInputParameters.getNumberOfAdults() + searchInputParameters.getNumberOfChildren() + searchInputParameters.getNumberOfInfants());
    }
}