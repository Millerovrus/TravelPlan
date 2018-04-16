package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.api.KiwiApi;
import com.netcracker.travelplanner.api.UFSParser;
import com.netcracker.travelplanner.api.YandexApi;
import com.netcracker.travelplanner.models.*;
import com.netcracker.travelplanner.executors.Executor;
import com.netcracker.travelplanner.models.entities.Edge;
import com.netcracker.travelplanner.models.entities.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class MainRouteService {
    @Autowired
    private Algorithm algorithm;

    @Value("${route-service.threads-count}")
    private Integer threadsCount;

    @Value("${route-service.timeout-minutes}")
    private Integer timeout;

    private final Logger logger = LoggerFactory.getLogger(MainRouteService.class);

//    private WebDriver driver = WebParser.getDriver();
//    private YandexParser yandexParser = new YandexParser(driver);
    private KiwiApi kiwiApi = new KiwiApi();
    private YandexApi yandexApi = new YandexApi();
    private UFSParser UFSParser = new UFSParser();

    private Executor executor1 = new Executor();
    private Executor executor2 = new Executor();
    private Executor executor3 = new Executor();

    private SearchInputParameters prepareInputData(String from, String to, String latLongFrom, String latLongTo, String date, int numberOfAdults, int numberOfChildren, int numberOfInfants){
        return new PreparingDataService().prepareData(from
                ,to
                ,latLongFrom
                ,latLongTo
                ,date
                ,numberOfAdults
                ,numberOfChildren
                ,numberOfInfants);
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
            e.printStackTrace();
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

    public Integer getThreadsCount() {
        return threadsCount;
    }

    public void setThreadsCount(Integer threadsCount) {
        this.threadsCount = threadsCount;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}