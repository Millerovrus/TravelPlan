package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.service.ApiService;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ApiFinalService {
    @Autowired
    private Algorithm algorithm;
    private WebDriver driver = WebParser.getDriver();
    private ApiServiceManager apiServiceManager;
    private ApiInterface apiInterface;

    private YandexExecutor yandexExecutor = new YandexExecutor();
    private YandexParserExecutor yandexParserExecutor = new YandexParserExecutor();
    private KiwiExecutor kiwiExecutor = new KiwiExecutor();

    private TaskManager taskManager;


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
        PreparingDataService preparingDataService = new PreparingDataService();
        taskManager = new TaskManager();
        InitializatorApi initializatorApi = preparingDataService.prepareData(from, to, latLongFrom, latLongTo, date );
        apiServiceManager = new ApiServiceManager(initializatorApi);
        apiServiceManager.setDriver(driver);
        List<Callable<List<Edge>>> list = apiServiceManager.getTasks(apiInterface);

        taskManager.setKiwiExecutor(kiwiExecutor);
        taskManager.setYandexExecutor(yandexExecutor);
        taskManager.setYandexParserExecutor(yandexParserExecutor);

        List<Future<List<Edge>>> futures = taskManager.executeTask(list);
        List<Edge> edgeList = new ArrayList<>();

        for (Future<List<Edge>> future : futures) {
            try {
                edgeList.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<Route> routeList = new ArrayList<>();

        for (int i = 0; i < RouteType.values().length ; i++) {

            boolean needSave = true;

            List<Edge> tempEdgeList = separator(edgeList,RouteType.values()[i]);

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
