package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.algorithms.Algorithm2;
import com.netcracker.travelplanner.api.KiwiApi;
import com.netcracker.travelplanner.webParsers.WebParser;
import com.netcracker.travelplanner.api.YandexApi;
import com.netcracker.travelplanner.api.YandexParser;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.executors.KiwiExecutor;
import com.netcracker.travelplanner.executors.YandexExecutor;
import com.netcracker.travelplanner.executors.YandexParserExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@Service
public class TaskManagerService {

    @Autowired
    private Algorithm2 algorithm;

//    private WebDriver driver = WebParser.getDriver();
    private ApiServiceManager apiServiceManager;


    @Autowired
    private YandexExecutor yandexExecutor;
//    @Autowired
//    private YandexParserExecutor yandexParserExecutor;
    @Autowired
    private KiwiExecutor kiwiExecutor;



    private  List<Edge> separator(List<Edge> edges, RouteType type){

        List<Edge> edgeList = new ArrayList<>();
        edges.stream().filter(l->l.getEdgeType().equals(type)).forEach(l -> {
            try {
                edgeList.add((Edge) l.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        return edgeList;
    }

    public List<Route> findTheBestRoutes(String from, String to, String latLongFrom, String latLongTo, String date){

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData(from, to, latLongFrom, latLongTo, date );

        apiServiceManager = new ApiServiceManager(initializatorApi);

        List<Edge> edgeList  = Collections.synchronizedList(new ArrayList<>());

//        YandexParser yandexParser = new YandexParser();
//        yandexParser.setWebDriver(driver);
        KiwiApi kiwiApi = new KiwiApi();
        YandexApi yandexApi = new YandexApi();


        executorService.execute( () ->
        edgeList.addAll(yandexExecutor.execute(apiServiceManager.getTasks(yandexApi))));


        executorService.execute( () ->
        edgeList.addAll(kiwiExecutor.execute(apiServiceManager.getTasks(kiwiApi))));


//        executorService.execute( () ->
//        edgeList.addAll(yandexParserExecutor.execute(apiServiceManager.getTasks(yandexParser))));

        executorService.shutdown();

        try {
            executorService.awaitTermination(4, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        for (int i = 0; i < RouteType.values().length ; i++) {
//
//            boolean needSave = true;
//
//            List<Edge> tempEdgeList = separator(edgeList,RouteType.values()[i]);
//
//            List<Edge> edges = algorithm.getMinimalRoute(tempEdgeList,from,to);
//
//            if (!routeList.isEmpty()){
//                double duration = 0.0;
//                double cost = 0.0;
//                for (Edge edge : edges) {
//                    cost += edge.getCost();
//                    duration += edge.getDuration();
//                }
//                for (Route route : routeList) {
//                    if (route.getCost() == cost && route.getDuration() == duration){
//                        needSave = false;
//                    }
//                }
//            }
//
//            if (needSave) {
//                edgeList.addAll(edges);
//                Route route = new Route(new Date(), from, to, RouteType.values()[i]);
//                Short order = 1;
//                for (Edge edge : edges) {
//                    edge.setEdgeOrder(order++);
//                    edge.setRoute(route);
//                    route.getEdges().add(edge);
//                    route.setCost(route.getCost() + edge.getCost());
//                    route.setDuration(route.getDuration() + edge.getDuration());
//                }
//                routeList.add(route);
//            }
//        }

        List<Route> routeList = new ArrayList<>();

        List<List<Edge>> listAllEdges = algorithm.getBestFoundRoutes(edgeList,initializatorApi.getFrom().getName(),initializatorApi.getTo().getName());

        int idRouteForView  = 0;

        for (List<Edge> lists : listAllEdges) {



//                edgeList.addAll(lists);
                Route route = new Route();
//                route.setEdges(lists);
                route.setIdRouteForView(idRouteForView);
                idRouteForView++;
                Short order = 1;
                for (Edge edge : lists) {
                    edge.setEdgeOrder(order++);
                    edge.setRoute(route);
                    route.getEdges().add(edge);
                    route.setCost(route.getCost() + edge.getCost());
                    route.setDuration(route.getDuration() + edge.getDuration());
                }
                routeList.add(route);
                }


        return routeList;
    }

}
