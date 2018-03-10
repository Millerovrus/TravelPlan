package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.service.TaskManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YandexExecutorTest {

    @Autowired
    TaskManagerService taskManagerService;


    @Test
    public void execute() throws Exception {

        List<Route> list = taskManagerService.findTheBestRoutes("Voronezh", "Berlin", "(51.6754966, 39.20888230000003)","(52.5174, 13.4068)","2018-03-22");

        list.forEach(System.out::println);

        System.out.println(list.size());

    }

}