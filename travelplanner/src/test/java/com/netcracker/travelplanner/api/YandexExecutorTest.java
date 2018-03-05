package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.entities.Edge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YandexExecutorTest {

    @Autowired
    ApiFinalService apiFinalService;


    @Test
    public void execute() throws Exception {
        /* "Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-15" */

        apiFinalService.findTheBestRoutes("Voronezh", "Moscow", "(51.6754966, 39.20888230000003)","(55.755826, 37.617299900000035)","2018-03-15")
                .forEach(route -> System.out.println(route.toString()));
    }

}