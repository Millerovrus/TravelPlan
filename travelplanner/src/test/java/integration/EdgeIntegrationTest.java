package integration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.netcracker.travelPlanner.TravelPlannerApplication;
import com.netcracker.travelPlanner.entities.Edge;
import com.netcracker.travelPlanner.service.EdgeRepositoryService;
import com.netcracker.travelPlanner.service.KiwiService;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EdgeIntegrationTest {


    @Autowired
    private EdgeRepositoryService edgeRepositoryService;

    @Test
    public void edgesSavingTest(){


        KiwiService kiwiService = new KiwiService();

        List<Edge> edges = kiwiService.getEdgesFlights("Moscow"
                ,"Voronezh"
                ,LocalDate.of(2018,02,01)
                ,LocalDate.of(2018,02,01));

        edges.forEach(l -> System.out.println(l.toString()));

        edgeRepositoryService.addAll(edges);
    }

}
