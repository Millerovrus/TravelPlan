package integrationTests;


import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.model.entities.*;
import com.netcracker.travelplanner.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EntitiesTest {

//    @Autowired
    private EdgeRepository edgeRepository;

//    @Autowired
    private PointRepository pointRepository;

//    @Autowired
    private TransitEdgeRepository transitEdgeRepository;

//    @Autowired
    private UserRepository userRepository;

//    @Autowired
    private RouteRepository routeRepository;


//    @Test
    public void writeEntitiesToDb(){

        User user = new User("m@m.com"
                ,"john"
                ,"johnson"
                ,new Date()
                ,false
                ,new Date()
                ,"123");

        Point point1 = new Point("Voronezh"
                ,0.0
                ,0.0
                ,"VOZ"
                ,"123"
                ,"Воронеж"
                , "America\\/Los_Angeles");

        Point point2 = new Point("Moscow"
                ,0.0
                ,0.0
                ,"MOV"
                ,"321"
                ,"Москва"
                , "America\\/Los_Angeles");

        TransitEdge transitEdge = new TransitEdge(point1,point2,LocalDateTime.now().plusHours(1),LocalDateTime.now());

        Edge edge = new Edge();
        edge.setCreationDate(new Date());
        edge.setTransportType("plane");
        edge.setDuration(1.0);
        edge.setCost(1000.0);
        edge.setStartDate(LocalDateTime.now().plusHours(2));
        edge.setEndDate(LocalDateTime.now());
        edge.setCurrency("rub");
        edge.setNumberOfTransfers(1);
        edge.setStartPoint(point1);
        edge.setEndPoint(point2);
        edge.setPurchaseLink("www.kupibilet.ru");



        List<TransitEdge> transitEdges = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        point1.setTransitEdgesEnd(transitEdges);
        point1.setEdgesEnd(edges);
        point2.setTransitEdgesStart(transitEdges);
        point2.setEdgesStart(edges);

        transitEdge.setEdge(edge);
        transitEdges.add(transitEdge);
        edge.setTransitEdgeList(transitEdges);

        edge.setStartPoint(point1);
        edge.setEndPoint(point2);



        edges.add(edge);

        Route route = new Route(new Date()
                ,"vor"
                ,"mosc"
                ,123);

        edge.setRoute(route);
        route.setEdges(edges);


        routeRepository.save(route);
        edgeRepository.save(edges);
        userRepository.save(user);
        transitEdgeRepository.save(transitEdges);
        pointRepository.save(point1);
        pointRepository.save(point2);

















    }
}
