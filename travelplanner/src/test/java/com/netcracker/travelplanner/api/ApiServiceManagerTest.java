package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.model.SearchInputParameters;
import com.netcracker.travelplanner.model.exceptions.KiwiIATACodeException;
import com.netcracker.travelplanner.services.PreparingDataService;
import org.junit.Test;

public class ApiServiceManagerTest {


//    @Test
    public void found(){

        PreparingDataService preparingDataService = new PreparingDataService();

        SearchInputParameters searchInputParameters = null, searchInputParameters2 = null, searchInputParameters3 = null;
        try {
            searchInputParameters2 = preparingDataService.prepareData("Moscow", "Voronezh", "(55.755826, 37.617299900000035)","(51.6754966, 39.20888230000003)","2018-03-16", 1,0,0);

            searchInputParameters3 = preparingDataService.prepareData("Voronezh", "Berlin", "(51.6754966, 39.20888230000003)","(52.5174, 13.4068)","2018-03-16", 1,0,0);

            searchInputParameters = preparingDataService.prepareData("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-30", 1, 0, 0);
        } catch (KiwiIATACodeException e) {
            e.printStackTrace();
        }

    }


}