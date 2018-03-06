package unitTests;

import com.netcracker.travelplanner.TravelPlannerApplication;
import com.netcracker.travelplanner.algorithms.*;
import com.netcracker.travelplanner.entities.*;
import com.netcracker.travelplanner.repository.*;
import com.netcracker.travelplanner.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

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

 /* @Autowired
  private RouteFinder routeFinder;*/

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
//    @Test
//    public void g(){
//
//        List<Route> list = routesFinalService.findTheBestRoutes("Voronezh","Moscow",LocalDate.of(2018,2,23));
//
//        list.forEach(System.out::println);
//
//        List<Edge> edgeList = new ArrayList<>();
//        list.forEach(route -> edgeList.addAll(route.getEdges()));
//
//        edgeList.forEach(System.out::println);
//
//        edgeRepository.save(edgeList);
//        routeRepository.save(list);
//    }


}
