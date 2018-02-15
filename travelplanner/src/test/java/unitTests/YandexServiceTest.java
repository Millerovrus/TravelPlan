package unitTests;

import com.netcracker.travelplanner.entities.RouteType;
import org.junit.Test;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.YandexService;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Test
    public void ggg(){

        System.out.println(RouteType.values().length);
        for (int i = 0; i < RouteType.values().length ; i++) {

            System.out.println(RouteType.values()[i]);

        }
    }
}
