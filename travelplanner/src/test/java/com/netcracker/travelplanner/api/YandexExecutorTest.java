package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.services.MainRouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YandexExecutorTest {

    @Autowired
    MainRouteService mainRouteService;


    @Test
    public void execute() throws Exception {

        mainRouteService.findBestRoutes("Voronezh", "Berlin", "(51.6754966, 39.20888230000003)","(52.5174, 13.4068)","2018-03-17", 1)
                .forEach(route -> System.out.println(route.toString()));
    }

}