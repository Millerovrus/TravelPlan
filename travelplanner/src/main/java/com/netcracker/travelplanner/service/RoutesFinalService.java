package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.api.*;
import com.netcracker.travelplanner.entities.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class RoutesFinalService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConvertPointsToListEdges convertPointsToListEdges;

    @Autowired
    private Algorithm algorithm;

    private WebDriver driver = WebParser.getDriver();


    private ApiServiceManager apiServiceManager;

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

    public List<Route> findTheBestRoutes(String from, String to, String latit, String longit, String date){

        logger.debug("Запуск поиска лучших маршрутов между from: " + from + " и to: " + to);

//        List<Edge> list = convertPointsToListEdges.findAll(from,to,localDate);

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData(from, to, latit, longit, date);

        apiServiceManager = new ApiServiceManager(initializatorApi);

        apiServiceManager.setDriver(driver);

        List<Edge> list = apiServiceManager.foundEdges();

        List<Edge> edgeList = new ArrayList<>();

        List<Route> routeList = new ArrayList<>();

        for (int i = 0; i < RouteType.values().length ; i++) {

            boolean needSave = true;

            List<Edge> tempEdgeList = separator(list,RouteType.values()[i]);

            List<Edge> edges = algorithm.getMinimalRoute(tempEdgeList,from,to);

            if (!routeList.isEmpty()){
                double duration = 0.0;
                double cost = 0.0;
                for (Edge edge : edges) {
                    cost += edge.getCost();
                    duration += edge.getDuration();
                }
                for (Route route : routeList) {
                    if (route.getCost() == cost && route.getDuration() == duration){
                        needSave = false;
                        logger.debug("Найденный маршрут повторяется и не будет сохранен.");
                    }
                }
            }

            if (needSave) {
                edgeList.addAll(edges);
                Route route = new Route(new Date(), from, to, RouteType.values()[i]);
                Short order = 1;
                for (Edge edge : edges) {
                    edge.setEdgeOrder(order++);
                    edge.setRoute(route);
                    route.getEdges().add(edge);
                    route.setCost(route.getCost() + edge.getCost());
                    route.setDuration(route.getDuration() + edge.getDuration());
                }
                routeList.add(route);
            }
        }
        return routeList;
    }
}
