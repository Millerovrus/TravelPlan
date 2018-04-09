package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.models.SearchInputParameters;
import com.netcracker.travelplanner.services.PreparingDataService;
import com.netcracker.travelplanner.webParsers.WebParser;
import org.junit.Test;

public class ApiServiceManagerTest {


    @Test
    public void found(){

        YandexParser parser = new YandexParser(WebParser.getDriver());

        PreparingDataService preparingDataService = new PreparingDataService();

        SearchInputParameters searchInputParameters = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-30", 1);

        SearchInputParameters searchInputParameters2 = preparingDataService.prepareData("Moscow", "Voronezh", "(55.755826, 37.617299900000035)","(51.6754966, 39.20888230000003)","2018-03-16", 1);

        SearchInputParameters searchInputParameters3 = preparingDataService.prepareData("Voronezh", "Berlin", "(51.6754966, 39.20888230000003)","(52.5174, 13.4068)","2018-03-16", 1);


        parser.findEdgesFromTo(searchInputParameters.getFrom(), searchInputParameters.getTo(), searchInputParameters.getDeparture(), searchInputParameters.getNumberOfPassengers()).forEach(edge -> System.out.println(edge.toString()));

        parser.findEdgesFromTo(searchInputParameters2.getFrom(), searchInputParameters2.getTo(), searchInputParameters2.getDeparture(), searchInputParameters2.getNumberOfPassengers()).forEach(edge -> System.out.println(edge.toString()));

        parser.findEdgesFromTo(searchInputParameters3.getFrom(), searchInputParameters3.getTo(), searchInputParameters3.getDeparture(), searchInputParameters3.getNumberOfPassengers()).forEach(edge -> System.out.println(edge.toString()));

    }


}