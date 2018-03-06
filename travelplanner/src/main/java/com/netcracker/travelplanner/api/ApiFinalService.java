package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.service.ApiService;
import javassist.bytecode.analysis.Executor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ApiFinalService {

    @Autowired
    private Algorithm algorithm;

    private WebDriver driver = WebParser.getDriver();
    private ApiServiceManager apiServiceManager;
//    private ApiInterface apiInterface;

    private YandexExecutor yandexExecutor = new YandexExecutor();
    private YandexParserExecutor yandexParserExecutor = new YandexParserExecutor();
    private KiwiExecutor kiwiExecutor = new KiwiExecutor();

//    private TaskManager taskManager;


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

//        taskManager = new TaskManager();

        InitializatorApi initializatorApi = preparingDataService.prepareData(from, to, latLongFrom, latLongTo, date );

        apiServiceManager = new ApiServiceManager(initializatorApi);

//        taskManager.setKiwiExecutor(kiwiExecutor);
//        taskManager.setYandexExecutor(yandexExecutor);
//        taskManager.setYandexParserExecutor(yandexParserExecutor);

//        List<Edge> edgeList = taskManager.executeTask(list);

        List<Edge> edgeList  = Collections.synchronizedList(new ArrayList<>());

        YandexParser yandexParser = new YandexParser();
        yandexParser.setWebDriver(driver);
        KiwiApi kiwiApi = new KiwiApi();
        YandexApi yandexApi = new YandexApi();


        executorService.execute( () ->
        edgeList.addAll(yandexExecutor.execute(apiServiceManager.getTasks(yandexApi))));


        executorService.execute( () ->
        edgeList.addAll(kiwiExecutor.execute(apiServiceManager.getTasks(kiwiApi))));


        executorService.execute( () ->
        edgeList.addAll(yandexParserExecutor.execute(apiServiceManager.getTasks(yandexParser))));

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
