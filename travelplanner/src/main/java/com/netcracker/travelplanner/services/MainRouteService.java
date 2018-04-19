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
    private final Algorithm algorithm;
    private final ErrorSavingService errorSavingService;

    @Value("${route-service.threads-count}")
    private Integer threadsCount;

    @Value("${route-service.timeout-minutes}")
    private Integer timeout;

    private final Logger logger = LoggerFactory.getLogger(MainRouteService.class);

    private KiwiApi kiwiApi = new KiwiApi();
    private YandexApi yandexApi = new YandexApi();
    private UFSParser UFSParser = new UFSParser();

    private final Executor executor1;
    private final Executor executor2;
    private final Executor executor3;

    @Autowired
    public MainRouteService(Algorithm algorithm, Executor executor1, Executor executor2, Executor executor3, ErrorSavingService errorSavingService) {
        this.algorithm = algorithm;
        this.executor1 = executor1;
        this.executor2 = executor2;
        this.executor3 = executor3;
        this.errorSavingService = errorSavingService;
    }

    private SearchInputParameters prepareInputData(String from, String to, String latLongFrom, String latLongTo, String date, int numberOfAdults, int numberOfChildren, int numberOfInfants){
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
            errorSavingService.saveError(new IntegrationError(description, new Date(), "EdgeService"));
        }
        return parameters;
    }

    private List<Task> getTasks(SearchInputParameters searchInputParameters){
        return new TaskCreatorManager(searchInputParameters).getTasks();
    }

    private List<Edge> getAllEdges(ExecutorService executorService, List<Task> taskList){

        List<Edge> edgeList  = Collections.synchronizedList(new ArrayList<>());

        executorService.execute( () ->{
            logger.debug("Start Thread yandexApi");
            edgeList.addAll(executor1.execute(taskList,yandexApi));
        });


        executorService.execute( () -> {
            logger.debug("Start Thread kiwiApi");
            edgeList.addAll(executor2.execute(taskList,kiwiApi));
        });


//        executorService.execute( () ->{
//            logger.debug("Start Thread yandexParser");
//            edgeList.addAll(executor3.execute(taskList,yandexParser));
//        });
        executorService.execute( () -> {
            logger.debug("Start Thread UFSParser");
            edgeList.addAll(executor3.execute(taskList,UFSParser));
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(6, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            errorSavingService.saveError(new IntegrationError(e.getMessage(), new Date(), "ExecutorService"));
        }

        return edgeList;
    }

    private List<Route> getRoutes(List<Edge> edgeList, String startPoint, String endPoint, int numberOfPassengers){
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