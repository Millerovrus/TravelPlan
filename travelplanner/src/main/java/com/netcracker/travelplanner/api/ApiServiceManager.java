package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.YandexService;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class ApiServiceManager {


//    private WebDriver driver;

    private InitializatorApi initializatorApi;

//    private KiwiApi kiwiApi = new KiwiApi();
//    private YandexParser yandexParser = new YandexParser();
//    private YandexApi yandexApi = new YandexApi();


//    private List<Edge> listEdge;

    public ApiServiceManager(InitializatorApi initializatorApi){
        this.initializatorApi = initializatorApi;
    }

    public List<Callable<List<Edge>>> getTasks(ApiInterface apiInterface){

        List<Callable<List<Edge>>> callables = new ArrayList<>();

        /* прямой рейс */
        callables.add( () -> apiInterface.findEdgesFromTo(initializatorApi.getFrom(), initializatorApi.getTo(), initializatorApi.getDeparture()));


        if (initializatorApi.isGlobalRoute()) {
        /* рейсы рядом с точкой отправдения */
            for (Point point : initializatorApi.getCitiesFrom()) {
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(initializatorApi.getFrom(), point, initializatorApi.getDeparture());
                callables.add(listCallable);

                /* из начальной точки в окружение конечной*/
                Callable<List<Edge>> listCallable2 = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture());
                callables.add(listCallable2);

            }

        /* рядом с точкой прибытия */
            for (Point point : initializatorApi.getCitiesTo()) {
                Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(point, initializatorApi.getTo(), initializatorApi.getDeparture());
                callables.add(listCallable);
            }

        /* перебор между всеми точками */
            for (Point pointFrom : initializatorApi.getCitiesFrom()) {

                for (Point poinTo : initializatorApi.getCitiesTo()) {
                    Callable<List<Edge>> listCallable = () -> apiInterface.findEdgesFromTo(pointFrom, poinTo, initializatorApi.getDeparture());
                    callables.add(listCallable);
                }
            }

        }

        return callables;
    }



//
//    public List<Edge> foundEdges(){
//
//        yandexParser.setWebDriver(driver);
//
//        listEdge = Collections.synchronizedList(new ArrayList<>());
//        ExecutorService executorService = Executors.newCachedThreadPool();
//
//        if(initializatorApi.isGlobalRoute()){
//
//           synchronized (listEdge) {
//               // task 1
//               executorService.execute(() -> {
//                   List<Edge> list = yandexApi.findEdgesOneToAll(initializatorApi.getFrom(), initializatorApi.getCitiesFrom(), initializatorApi.getDeparture());
//                   if (!list.isEmpty()) { listEdge.addAll(list); }
//               });
//               // task 2
//               executorService.execute(() -> {
//                   List<Edge> list = yandexApi.findEdgesAllToOne(initializatorApi.getCitiesTo(), initializatorApi.getTo(), initializatorApi.getDeparture());
//                   if (!list.isEmpty()) { listEdge.addAll(list); }
//               });
//               // task 3
//            /*   executorService.execute(()->{
//                   List<Edge> list = yandexParser.findEdgesOneToAll(initializatorApi.getFrom(), initializatorApi.getCitiesFrom(), initializatorApi.getDeparture());
//                   if(!list.isEmpty()){ listEdge.addAll(list);}
//               });
//               // task 4
//               executorService.execute(()->{
//                   List<Edge> list = yandexParser.findEdgesAllToOne(initializatorApi.getCitiesTo(), initializatorApi.getTo(), initializatorApi.getDeparture());
//                   if(!list.isEmpty()){ listEdge.addAll(list);}
//               });*/
//               // task 5
//               executorService.execute(()->{
//                   List<Edge> list = kiwiApi.findEdgesAllToAll(initializatorApi.getCitiesFrom(),initializatorApi.getCitiesTo(),initializatorApi.getDeparture());
//                   if(!list.isEmpty()) { listEdge.addAll(list); }
//               });
//
//               executorService.execute(()-> {
//                   List<Edge> list = yandexParser.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture());
//                   if(!list.isEmpty()) { listEdge.addAll(list); }
//               });
//
//               executorService.execute(()-> {
//                   List<Edge> list = yandexApi.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture());
//                   if(!list.isEmpty()) { listEdge.addAll(list); }
//               });
//
//               executorService.execute(()-> {
//                   List<Edge> list = kiwiApi.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture());
//                   if(!list.isEmpty()) { listEdge.addAll(list); }
//               });
//
//
//           }
//        }
//        else {
//            synchronized (listEdge){
//                // task 1
//                executorService.execute(()->{
//                    List<Edge> list = yandexApi.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture());
//                    if(!list.isEmpty()) { listEdge.addAll(list); }
//                });
//            }
//        }
//
//        executorService.shutdown();
//
//        try {
//            executorService.awaitTermination(30, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return listEdge;
//    }



//    public WebDriver getDriver() {
//        return driver;
//    }
//
//    public void setDriver(WebDriver driver) {
//        this.driver = driver;
//    }


}
