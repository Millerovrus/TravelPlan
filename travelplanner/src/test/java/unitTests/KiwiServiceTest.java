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

        List<Edge> edges = kiwiService.getEdgesFlights("moscow"
                ,"voronezh"
                , LocalDate.of(2018,02,01)
                , LocalDate.of(2018,02,03));

        edges.forEach(l -> System.out.println(l.toString()));

    }

}
