package unitTests;


import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.algorithms.RouteFinder;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Route;
import com.netcracker.travelplanner.entities.RouteEdge;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.repository.EdgeRepository;
import com.netcracker.travelplanner.repository.RouteRepository;
import com.netcracker.travelplanner.service.ApiService;
import com.netcracker.travelplanner.service.ConvertPointsToListEdges;
import com.netcracker.travelplanner.service.RouteRepositoryService;
import com.netcracker.travelplanner.service.RoutesFinalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TravelPlannerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test_15_02_2018 {

  @Autowired
  private ApiService apiService;

  @Autowired
  private EdgeRepository edgeRepository;

  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private ConvertPointsToListEdges convertPointsToListEdges;

  @Autowired
  private Algorithm algorithm;

  @Autowired
  private RouteFinder routeFinder;

  @Autowired
  private RoutesFinalService routesFinalService;


  @Test
    public void getClosesCities(){
      List<String> li = apiService.getClosesCities("Moscow");
      if(li!=null)
      li.forEach(System.out::println);
  }

  @Test
    public void getIATAcode(){
      String s = apiService.cityToIataCode("Lipetsk");
      System.out.println(s);
  }

  @Test
    public void getEdgesFromApi(){

        List<Edge> list = apiService.getEdgesFromTo("Voronezh","Riga",LocalDate.of(2018,3,1));

        if(list!=null) {
            list.forEach(l -> System.out.println(l.getStartPoint() + "    "
                    + l.getDestinationPoint() + " "
                    + l.getWeight() + "   "
                    + l.getEdgeType()));
        }
        else System.out.println("null list!!!");
    }



    @Test
    public void gogogogog(){

        List<Edge> list = convertPointsToListEdges.findAll("Voronezh","Berlin",LocalDate.of(2018,3,1));


        List<Route> list1 = routeFinder.findRoutes(list,"VOZ","BER");

        list1.forEach(l-> System.out.println(l.toString()));

        for (Route aList1 : list1) {
            List<Edge> edgeList = new ArrayList<>();
            aList1.getRouteEdges().forEach(l -> edgeList.add(l.getEdge()));
            edgeRepository.save(edgeList);
        }

        routeRepository.save(list1);


    }

    @Test
    public void testAddEdgesRoutes_15_02_2018(){
//
//        String from = "Voronezh";
//        String to = "Berlin";
//        LocalDate localDate = LocalDate.of(2018,3,1);
//
//        List<Edge> list = convertPointsToListEdges.findAll(from,to,localDate);
//
//        List<Edge> edgeList = new ArrayList<>();
//
//        List<Route> routeList = new ArrayList<>();
//
//        for (int i = 0; i < RouteType.values().length ; i++) {
//
//          List<Edge> tempEdgeList = (RoutesFinalService.separator(list,RouteType.values()[i]));
//
//          List<Edge> edges = algorithm.getMinimalRoute(tempEdgeList,"VOZ","BER");
//
//          edgeList.addAll(edges);
//
//            Route route = new Route(new Date(),from,to,RouteType.values()[i]);
//
//            int order = 1;
//            for (Edge Edges : edges) {
//
//                RouteEdge routeEdge = new RouteEdge(order++);
//                routeEdge.setRoute(route);
//                routeEdge.setEdge(Edges);
//                route.getRouteEdges().add(routeEdge);
//                route.setCost(+Edges.getCost());
//                route.setDuration(+Edges.getDuration());
//            }
//
//            routeList.add(route);
//        }
//
//        if(!edgeList.isEmpty()&&!routeList.isEmpty()) {
//            edgeRepository.save(edgeList);
//            routeRepository.save(routeList);
//        }
//        else System.out.println("empties lists");
//

    }
    @Test
    public void g(){

        List<Route> list = routesFinalService.findTheBestRoutes("Voronezh","Riga",LocalDate.of(2018,3,1));

        list.forEach(System.out::println);

        List<Edge> edgeList = new ArrayList<>();
        list.forEach(route -> route.getRouteEdges().forEach(routeEdge -> edgeList.add(routeEdge.getEdge())));

        edgeList.forEach(System.out::println);

        edgeRepository.save(edgeList);
        routeRepository.save(list);
    }



}
