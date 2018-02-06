package unitTests;

import org.junit.Test;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.YandexService;

import java.time.LocalDate;
import java.util.List;

public class YandexServiceTest {

    @Test
    public void yandexSearchTest(){

        YandexService yandexService = new YandexService("64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3");

        List<Edge> edgeList = yandexService.getEdgesFromYandex("c193"
        ,"c143"
        ,LocalDate.of(2018,02,15));

        edgeList.forEach(l -> System.out.println(l.toString()));

        System.out.println(edgeList.size());

    }
}
