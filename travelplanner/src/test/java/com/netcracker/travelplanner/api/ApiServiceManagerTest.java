package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.model.SearchInputParameters;
import com.netcracker.travelplanner.model.exceptions.KiwiIATACodeException;
import com.netcracker.travelplanner.services.PreparingDataService;
import com.netcracker.travelplanner.webParsers.WebParser;
import org.junit.Test;

public class ApiServiceManagerTest {


//    @Test
    public void found(){

        YandexParser parser = new YandexParser(WebParser.getDriver());

        PreparingDataService preparingDataService = new PreparingDataService();

        SearchInputParameters searchInputParameters = null, searchInputParameters2 = null, searchInputParameters3 = null;
        try {
            searchInputParameters2 = preparingDataService.prepareData("Moscow", "Voronezh", "(55.755826, 37.617299900000035)","(51.6754966, 39.20888230000003)","2018-03-16", 1,0,0);

            searchInputParameters3 = preparingDataService.prepareData("Voronezh", "Berlin", "(51.6754966, 39.20888230000003)","(52.5174, 13.4068)","2018-03-16", 1,0,0);

            searchInputParameters = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-30", 1, 0, 0);
        } catch (KiwiIATACodeException e) {
            e.printStackTrace();
        }

        if (searchInputParameters != null) {
            parser.findEdgesFromTo(searchInputParameters.getFrom(), searchInputParameters.getTo(), searchInputParameters.getDeparture(), searchInputParameters.getNumberOfAdults(), searchInputParameters.getNumberOfChildren(), searchInputParameters.getNumberOfInfants()).forEach(edge -> System.out.println(edge.toString()));
        }

        if (searchInputParameters2 != null) {
            parser.findEdgesFromTo(searchInputParameters2.getFrom(), searchInputParameters2.getTo(), searchInputParameters2.getDeparture(), searchInputParameters2.getNumberOfAdults(), searchInputParameters2.getNumberOfChildren(), searchInputParameters2.getNumberOfInfants()).forEach(edge -> System.out.println(edge.toString()));
        }

        if (searchInputParameters3 != null) {
            parser.findEdgesFromTo(searchInputParameters3.getFrom(), searchInputParameters3.getTo(), searchInputParameters3.getDeparture(), searchInputParameters3.getNumberOfAdults(), searchInputParameters3.getNumberOfChildren(), searchInputParameters3.getNumberOfInfants()).forEach(edge -> System.out.println(edge.toString()));
        }

    }


}