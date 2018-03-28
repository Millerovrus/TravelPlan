package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.api.KiwiApi;
import com.netcracker.travelplanner.webParsers.WebParser;
import com.netcracker.travelplanner.api.YandexApi;
import com.netcracker.travelplanner.api.YandexParser;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.executors.Executor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class MainRouteService {

    private final Logger logger = LoggerFactory.getLogger(MainRouteService.class);

    private WebDriver driver = WebParser.getDriver();
    private YandexParser yandexParser = new YandexParser(driver);
    private KiwiApi kiwiApi = new KiwiApi();
    private YandexApi yandexApi = new YandexApi();

    private Executor executor1 = new Executor();
    private Executor executor2 = new Executor();
    private Executor executor3 = new Executor();
//    private ApiServiceManager apiServiceManager;

//    @Autowired
//    private YandexExecutor yandexExecutor;
//    @Autowired
//    private YandexParserExecutor yandexParserExecutor;
//    @Autowired
//    private KiwiExecutor kiwiExecutor;
//    @Deprecated
//    public List<Route> findTheBestRoutesss(String from, String to, String latLongFrom, String latLongTo, String date, int numberOfAdults, int numberOfChildren)
//    {
//
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//
//        PreparingDataService preparingDataService = new PreparingDataService();
//
//        SearchInputParameters searchInputParameters = preparingDataService.prepareData(from, to, latLongFrom, latLongTo, date, numberOfAdults, numberOfChildren);
//
//        apiServiceManager = new ApiServiceManager(searchInputParameters);
//
//        List<Edge> edgeList  = Collections.synchronizedList(new ArrayList<>());
//
//        YandexParser yandexParser = new YandexParser();
//        yandexParser.setWebDriver(driver);
//        KiwiApi kiwiApi = new KiwiApi();
//        YandexApi yandexApi = new YandexApi();
//
//
//        executorService.execute( () ->{
//            logger.debug("Start Thread yandexApi");
//            edgeList.addAll(yandexExecutor.execute(apiServiceManager.getTasks(yandexApi)));
//        });
//
//
//        executorService.execute( () -> {
//            logger.debug("Start Thread kiwiApi");
//            edgeList.addAll(kiwiExecutor.execute(apiServiceManager.getTasks(kiwiApi)));
//        });
//
//
//        executorService.execute( () ->{
//            logger.debug("Start Thread yandexParser");
//            edgeList.addAll(yandexParserExecutor.execute(apiServiceManager.getTasks(yandexParser)));
//        });
//
//        executorService.shutdown();
//
//        try {
//            executorService.awaitTermination(3, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return algorithm.getOptimalFoundRoutes(edgeList, searchInputParameters.getFrom().getName(), searchInputParameters.getTo().getName());
//    }

    private SearchInputParameters prepareInputData(String from, String to, String latLongFrom, String latLongTo, String date, int numberOfAdults, int numberOfChildren){
        return new PreparingDataService().prepareData(from
                ,to
                ,latLongFrom
                ,latLongTo
                ,date
                ,numberOfAdults
                ,numberOfChildren);
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


        executorService.execute( () ->{
            logger.debug("Start Thread yandexParser");
            edgeList.addAll(executor3.execute(taskList,yandexParser));
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
        return new Algorithm().getOptimalFoundRoutes(edgeList,startPoint,endPoint,numberOfPassengers);
    }

    public List<Route> findBestRoutes(String from
            ,String to
            ,String latLongFrom
            ,String latLongTo
            ,String date
            ,int numberOfAdults
            ,int numberOfChildren
            )
    {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        SearchInputParameters searchInputParameters = prepareInputData(from
                ,to
                ,latLongFrom
                ,latLongTo
                ,date
                ,numberOfAdults
                ,numberOfChildren);

        List<Task> taskList = getTasks(searchInputParameters);

        List<Edge> edgeList = getAllEdges(executorService,taskList);

        return getRoutes(edgeList
                ,searchInputParameters.getFrom().getName()
                ,searchInputParameters.getTo().getName()
                ,searchInputParameters.getNumberOfAdults()+searchInputParameters.getNumberOfChildren());

    }

}