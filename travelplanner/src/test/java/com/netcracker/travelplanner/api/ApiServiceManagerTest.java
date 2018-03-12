package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.service.InitializatorApi;
import com.netcracker.travelplanner.service.PreparingDataService;
import com.netcracker.travelplanner.webParsers.WebParser;
import org.junit.Test;

public class ApiServiceManagerTest {


    @Test
    public void found(){

        YandexParser parser = new YandexParser();

        parser.setWebDriver(WebParser.getDriver());

        PreparingDataService preparingDataService = new PreparingDataService();

        InitializatorApi initializatorApi = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-30");

        InitializatorApi initializatorApi2 = preparingDataService.prepareData("Moscow", "Voronezh", "(55.755826, 37.617299900000035)","(51.6754966, 39.20888230000003)","2018-03-16");

        InitializatorApi initializatorApi3 = preparingDataService.prepareData("Voronezh", "Berlin", "(51.6754966, 39.20888230000003)","(52.5174, 13.4068)","2018-03-16");


        parser.findEdgesFromTo(initializatorApi.getFrom(),initializatorApi.getTo(),initializatorApi.getDeparture()).forEach(edge -> System.out.println(edge.toString()));

        parser.findEdgesFromTo(initializatorApi2.getFrom(),initializatorApi2.getTo(),initializatorApi2.getDeparture()).forEach(edge -> System.out.println(edge.toString()));

        parser.findEdgesFromTo(initializatorApi3.getFrom(),initializatorApi3.getTo(),initializatorApi3.getDeparture()).forEach(edge -> System.out.println(edge.toString()));

    }


}