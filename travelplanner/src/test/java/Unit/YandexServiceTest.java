package Unit;

import org.junit.Test;
import ru.netcracker.travelPlanner.entities.Edge;
import ru.netcracker.travelPlanner.service.YandexService;

import java.time.LocalDate;
import java.util.List;

public class YandexServiceTest {

    @Test
    public void yandexSearchTest(){

        YandexService yandexService = new YandexService("64d2c4dc-e05a-4574-b51a-bdc03b2bc8a3");

        List<Edge> edgeList = yandexService.getEdgesFromYandex("c193"
        ,"c213"
        ,LocalDate.of(2018,02,05));

        edgeList.forEach(l -> System.out.println(l.toString()));

        System.out.println(edgeList.size());

    }
}
