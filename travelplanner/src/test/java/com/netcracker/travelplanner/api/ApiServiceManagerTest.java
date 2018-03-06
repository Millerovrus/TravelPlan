package com.netcracker.travelplanner.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApiServiceManagerTest {

/*
    @Test
    public void foundEdges() throws Exception {

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-15");

        ApiServiceManager apiServiceManager = new ApiServiceManager(initializatorApi);

        apiServiceManager.setDriver(WebParser.getDriver());

        apiServiceManager.foundEdges().forEach(edge -> System.out.println(edge.toString()));


    }*/

    @Test
    public void found(){

        YandexParser parser = new YandexParser();

        parser.setWebDriver(WebParser.getDriver());

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-18");

        InitializatorApi initializatorApi2 = preparingDataService.prepareData("Moscow", "Voronezh", "(55.755826, 37.617299900000035)","(51.6754966, 39.20888230000003)","2018-03-16");

        InitializatorApi initializatorApi3 = preparingDataService.prepareData("Voronezh", "Berlin", "(51.6754966, 39.20888230000003)","(52.5174, 13.4068)","2018-03-16");


        parser.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture()).forEach(edge -> System.out.println(edge.toString()));

        parser.findEdgesFromTo(initializatorApi2.getFrom(),initializatorApi.getTo(),initializatorApi2.getDeparture()).forEach(edge -> System.out.println(edge.toString()));

        parser.findEdgesFromTo(initializatorApi3.getFrom(),initializatorApi3.getTo(),initializatorApi3.getDeparture()).forEach(edge -> System.out.println(edge.toString()));

    }


    @Test
    public void gogog(){




    }

}