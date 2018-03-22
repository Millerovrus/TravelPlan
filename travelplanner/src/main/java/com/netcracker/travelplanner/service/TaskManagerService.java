package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.api.KiwiApi;
import com.netcracker.travelplanner.webParsers.WebParser;
import com.netcracker.travelplanner.api.YandexApi;
import com.netcracker.travelplanner.api.YandexParser;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.executors.KiwiExecutor;
import com.netcracker.travelplanner.executors.YandexExecutor;
import com.netcracker.travelplanner.executors.YandexParserExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class TaskManagerService {

    private final Logger logger = LoggerFactory.getLogger(TaskManagerService.class);

    @Autowired
    private Algorithm algorithm;

    private WebDriver driver = WebParser.getDriver();
    private ApiServiceManager apiServiceManager;

    @Autowired
    private YandexExecutor yandexExecutor;
    @Autowired
    private YandexParserExecutor yandexParserExecutor;
    @Autowired
    private KiwiExecutor kiwiExecutor;

    public List<Route> findTheBestRoutes(String from, String to, String latLongFrom, String latLongTo, String date){

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData(from, to, latLongFrom, latLongTo, date );

        apiServiceManager = new ApiServiceManager(initializatorApi);

        List<Edge> edgeList  = Collections.synchronizedList(new ArrayList<>());

        YandexParser yandexParser = new YandexParser();
        yandexParser.setWebDriver(driver);
        KiwiApi kiwiApi = new KiwiApi();
        YandexApi yandexApi = new YandexApi();


        executorService.execute( () ->{
            logger.debug("Start Thread yandexApi");
            edgeList.addAll(yandexExecutor.execute(apiServiceManager.getTasks(yandexApi)));
        });


        executorService.execute( () -> {
            logger.debug("Start Thread kiwiApi");
            edgeList.addAll(kiwiExecutor.execute(apiServiceManager.getTasks(kiwiApi)));
        });


        executorService.execute( () ->{
            logger.debug("Start Thread yandexParser");
            edgeList.addAll(yandexParserExecutor.execute(apiServiceManager.getTasks(yandexParser)));
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(3, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return algorithm.getOptimalFoundRoutes(edgeList,initializatorApi.getFrom().getName(),initializatorApi.getTo().getName());
    }
}