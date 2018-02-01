package integration;

import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.entities.RouteEdge;
import com.netcracker.travelplanner.entities.User;
import com.netcracker.travelplanner.repository.EdgeRepository;
import com.netcracker.travelplanner.repository.RouteRepository;
import com.netcracker.travelplanner.repository.UserRepository;
import com.netcracker.travelplanner.service.EdgeRepositoryService;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class WritingAllEntytiesToDb {

    @Autowired
    EdgeRepository edgeRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testDb() throws ParseException {


        Date date = new Date();

        /*создаем пользователей*/
        List<User> userList = new ArrayList<>();
        User u = new User("john@mail.com","John","Jonson",new Date(),false, date,"12345");
        User u1 = new User("johnG@mail.com","John","Gibson",new Date(),false, date,"12345");
        userList.add(u);
        userList.add(u1);

        /*Создаем список рёбер*/
        List<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(date,"москва","воронеж","plane",10.00,1000.00,100.0,date,date,"Eur",1));
        edgeList.add(new Edge(date,"воронеж","белгород","plane",10.00,1000.00,100.0,date,date,"Eur",1));
        edgeList.add(new Edge(date,"москва","берлин","plane",10.00,1000.00,100.0,date,date,"Eur",1));
        edgeList.add(new Edge(date,"белгород","москва","plane",10.00,1000.00,100.0,date,date,"Eur",1));
        edgeList.add(new Edge(date,"харьков","белгород","train",10.00,1000.00,100.0,date,date,"Eur",1));
        edgeList.add(new Edge(date,"берлин","москва","bus",10.00,1000.00,100.0,date,date,"Eur",1));

        /*создаем список маршрутов*/
        List<Route> routeList = new ArrayList<>();
        routeList.add(new Route(date,"воронеж","берлин",1));
        routeList.add(new Route(date,"берлин","воронеж",1));

        /*добавляем ребра к маршруту*/
        RouteEdge routeEdge = new RouteEdge(123);
        routeEdge.setRoute(routeList.get(0));
        routeEdge.setEdge(edgeList.get(1));

        RouteEdge routeEdge2 = new RouteEdge(123);
        routeEdge2.setRoute(routeList.get(0));
        routeEdge2.setEdge(edgeList.get(3));

        RouteEdge routeEdge3 = new RouteEdge(123);
        routeEdge3.setRoute(routeList.get(0));
        routeEdge3.setEdge(edgeList.get(2));

        /*добавляем связь маршрута к рёбрам*/
        routeList.get(0).getRouteEdges().add(routeEdge);
        routeList.get(0).getRouteEdges().add(routeEdge2);
        routeList.get(0).getRouteEdges().add(routeEdge3);

        /*добавляем пользователя к маршруту*/
        routeList.get(0).setUser(u);
        /*связь пользователя и маршрутов*/
        u.getRoutes().add(routeList.get(0));

//        userRepository.deleteAll();
//        edgeRepository.deleteAll();
//        routeRepository.deleteAll();



        /*сохраняем всё в таблицы бд*/
        edgeRepository.save(edgeList);

        userRepository.save(u);

        userRepository.save(u1);

        routeRepository.save(routeList);



//        Assert.assertArrayEquals(edgeRepository.findAll().toArray(), edgeList.toArray());
//        Assert.assertArrayEquals(routeRepository.findAll().toArray(), routeList.toArray());
//        Assert.assertArrayEquals(userRepository.findAll().toArray(),userList.toArray());


//        userRepository.delete(u);

    }
}
