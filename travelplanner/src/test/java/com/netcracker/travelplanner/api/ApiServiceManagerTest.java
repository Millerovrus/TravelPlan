package com.netcracker.travelplanner.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApiServiceManagerTest {


    @Test
    public void foundEdges() throws Exception {

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-15");

        ApiServiceManager apiServiceManager = new ApiServiceManager(initializatorApi);

        apiServiceManager.setDriver(WebParser.getDriver());

        apiServiceManager.foundEdges().forEach(edge -> System.out.println(edge.toString()));


    }

    @Test
    public void found(){

        YandexParser parser = new YandexParser();

        parser.setWebDriver(WebParser.getDriver());

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-15");

        parser.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture()).forEach(edge -> System.out.println(edge.toString()));


    }

}