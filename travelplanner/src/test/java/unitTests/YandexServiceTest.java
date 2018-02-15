package unitTests;

import org.junit.Test;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.YandexService;

import java.time.LocalDate;
import java.util.List;


public class YandexServiceTest {

    @Test
    public void yandexSearchTest(){

        YandexService yandexService = new YandexService();

        List<Edge> edgeList = yandexService.getEdgesFromYandex("VOZ"
        ,"LPK"
        ,LocalDate.of(2018,2,15));

        edgeList.forEach(l -> System.out.println(l.toString()));

        System.out.println(edgeList.size());

    }
}
