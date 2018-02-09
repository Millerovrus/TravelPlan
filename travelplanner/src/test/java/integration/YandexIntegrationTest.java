package integration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.EdgeRepositoryService;
import com.netcracker.travelplanner.service.YandexService;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class YandexIntegrationTest {

    @Autowired
    private EdgeRepositoryService edgeRepositoryService;

    @Test
    public void edgesSavingTest(){


        YandexService yandexService = new YandexService("64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3");

        List<Edge> edgeList = yandexService.getEdgesFromYandex("c193"
                ,"c213"
                ,LocalDate.of(2018,02,10));

        edgeList.forEach(l -> System.out.println(l.toString()));

        System.out.println(edgeList.size());

        edgeRepositoryService.addAll(edgeList);
    }
}
