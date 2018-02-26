package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.YandexService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ApiServiceManager implements ApiServiceInterface {
    @Autowired
    private YandexService yandexService;

    private InitializatorApi initializatorApi;

    private KiwiApi kiwiApi = new KiwiApi(initializatorApi);
    private YandexParser yandexParser = new YandexParser(initializatorApi);
    private YandexApi yandexApi = new YandexApi(initializatorApi);

    private List<Edge> listEdge;

    public ApiServiceManager(InitializatorApi initializatorApi){
        this.initializatorApi = initializatorApi;
    }

    public List<Thread> getTaskList() {
        return null;
    }

    /*  если false то вызываем яндекс апи и яндекс расписания метод fromTo в 2 отдельных потока;
        если true то создаем потоки:
        1- яндексапи fromOnetoAll
        2- яндексапи fromAlltoOne
        3,4- такие же из яндексПарсер
        5- fromAlltoAll из киви */

    public List<Edge> foundEdges(){
        listEdge = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(7);

        if(initializatorApi.isGlobalRoute()){

            /*Runnable task1 = () -> {
                List<Edge> list = yandexApi.findEdgesOneToAll();
                synchronized (listEdge){
                    if (!list.isEmpty()) {
                        listEdge.addAll(list);
                    }
                }
            }; */

           synchronized (listEdge) {
               // task 1
               executorService.execute(() -> {
                   List<Edge> list = yandexApi.findEdgesOneToAll();
                   if (!list.isEmpty()) { listEdge.addAll(list); }
               });
               // task 2
               executorService.execute(() -> {
                   List<Edge> list = yandexApi.findEdgesAllToOne();
                   if (!list.isEmpty()) { listEdge.addAll(list); }
               });
               // task 3
               executorService.execute(()->{
                   List<Edge> list = yandexParser.findEdgesOneToAll();
                   if(!list.isEmpty()){ listEdge.addAll(list);}
               });
               // task 4
               executorService.execute(()->{
                   List<Edge> list = yandexParser.findEdgesAllToOne();
                   if(!list.isEmpty()){ listEdge.addAll(list);}
               });
               // task 5
               executorService.execute(()->{
                   List<Edge> list = kiwiApi.findEdgesAllToAll();
                   if(!list.isEmpty()) { listEdge.addAll(list); }
               });
           }
        }
        else {
            synchronized (listEdge){
                // task 1
                executorService.execute(()->{
                    List<Edge> list = yandexApi.findEdgesFromTo();
                    if(!list.isEmpty()) { listEdge.addAll(list); }
                });
                // task 2
                executorService.execute(()->{ // нужно добавить правильный метод
                    List<Edge> list = yandexService.getEdgesFromYandex(
                            initializatorApi.getFrom(),
                            initializatorApi.getTo(),
                            initializatorApi.getDeparture(),
                            initializatorApi.getYandexCodeFrom(),
                            initializatorApi.getYandexCodeTo());
                    if(!list.isEmpty()) { listEdge.addAll(list); }
                });
            }
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return listEdge;
    }

    public List<Edge> runTasks(List<Thread> tasks){
        return null;
    }
}
