package unitTests;

import org.junit.Test;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.KiwiService;

import java.time.LocalDate;
import java.util.List;


public class KiwiServiceTest {

    @Test
    public void kiwiFlightsSearchTest(){

        KiwiService kiwiService = new KiwiService();

        List<Edge> edges = kiwiService.getEdgesFlights("MOW"
                ,"VOZ"
                , LocalDate.of(2018,2,26)
                , LocalDate.of(2018,2,26));

        edges.forEach(l -> System.out.println(l.toString()));

    }

}
