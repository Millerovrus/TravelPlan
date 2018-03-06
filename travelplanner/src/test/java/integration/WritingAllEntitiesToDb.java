package integration;

import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.repository.*;
import com.netcracker.travelplanner.service.EdgeRepositoryService;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import com.netcracker.travelplanner.service.UserRepositoryService;
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

public class WritingAllEntitiesToDb {

    @Autowired
    EdgeRepositoryService edgeRepositoryService;

    @Autowired
    RouteRepositoryService routeRepositoryService;

    @Autowired
    UserRepositoryService userRepositoryService;

 /*   @Test
    public void testDb() throws ParseException {


        Date date = new Date();

        *//*создаем пользователей*//*
        List<User> userList = new ArrayList<>();
        User u = new User("johnny@mail.com","John","Jonson",new Date(),false, date,"12345");
        User u1 = new User("johnGuu@mail.com","John","Gibson",new Date(),false, date,"12345");
        userList.add(u);
        userList.add(u1);

        *//*Создаем список рёбер*//*
        List<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(date,"москва","воронеж", "plane",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"воронеж","белгород","plane", 10.00,1000.00,100.0,date,date,"Eur", RouteType.cheap));
        edgeList.add(new Edge(date,"москва","берлин","plane",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"белгород","москва","plane",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"харьков","белгород","train",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"берлин","москва","bus",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));

        *//*создаем список маршрутов*//*
        List<Route> routeList = new ArrayList<>();
        Date newDate = new Date();
        routeList.add(new Route(newDate,"воронеж","берлин", RouteType.cheap, 100.0, 200.0, 1500.0));
        routeList.add(new Route(newDate,"берлин","воронеж", RouteType.comfort, 250.0, 300.0, 400.0));
        routeList.add(new Route(newDate,"moscow","praga", RouteType.cheap, 100.0, 200.0, 1500.0));
        routeList.add(new Route(newDate,"moscow","praga", RouteType.optimal, 255.0, 300.0, 400.0));
        routeList.add(new Route(newDate,"moscow","berlin", RouteType.cheap, 100.0, 200.0, 1500.0));
        routeList.add(new Route(newDate,"moscow","berlin", RouteType.comfort, 250.0, 300.0, 400.0));






        *//*связываем ребра и маршрут*//*
        edgeList.get(1).setRoute(routeList.get(0));
        edgeList.get(1).setEdgeOrder((short)123);
        routeList.get(0).getEdges().add(edgeList.get(1));

        edgeList.get(3).setRoute(routeList.get(0));
        edgeList.get(3).setEdgeOrder((short)123);
        routeList.get(0).getEdges().add(edgeList.get(3));

        edgeList.get(2).setRoute(routeList.get(0));
        edgeList.get(2).setEdgeOrder((short)123);
        routeList.get(0).getEdges().add(edgeList.get(2));

        *//*добавляем пользователя к маршруту*//*
        routeList.get(0).setUser(u);
        *//*связь пользователя и маршрутов*//*
        u.getRoutes().add(routeList.get(0));

        *//*сохраняем всё в таблицы бд*//*
        userRepositoryService.save(u);
        userRepositoryService.save(u1);
        routeRepositoryService.save(routeList);

        edgeRepositoryService.save(edgeList.get(3));
        edgeRepositoryService.save(edgeList.get(1));
        edgeRepositoryService.save(edgeList.get(2));

//        userRepository.delete(u);

    }

    @Test
    public void testAddDbEdges() throws ParseException {
        Date date = new Date();
        List<Edge> edgeList = new ArrayList<>();
        edgeRepositoryService.deleteAll();
        edgeList.add(new Edge(date,"москва","воронеж", "plane",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"воронеж","белгород","plane", 10.00,1000.00,100.0,date,date,"Eur", RouteType.cheap));
        edgeList.add(new Edge(date,"москва","берлин","plane",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"белгород","москва","plane",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"харьков","белгород","train",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"берлин","москва","bus",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));

        *//* test for angular*//*
        edgeList.add(new Edge(date,"voronezh","moscow","bus",12.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"voronezh","moscow","plane",2.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"voronezh","moscow","train",11.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));
        edgeList.add(new Edge(date,"voronezh","moscow","bus",10.00,1000.00,100.0,date,date,"Eur",RouteType.cheap));

        edgeRepositoryService.save(edgeList);
    }*/

    @Test
    public void t(){

        Route route = new Route(new Date(),"vor","msk",RouteType.cheap);

        routeRepositoryService.save(route);
    }
}
