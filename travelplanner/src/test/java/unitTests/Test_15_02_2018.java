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

        List<Edge> list = apiService.getEdgesFromTo("Voronezh","Riga");

        if(list!=null) {
            list.forEach(l -> System.out.println(l.getStartPoint() + "    "
                    + l.getDestinationPoint() + " "
                    + l.getWeight() + "   "
                    + l.getEdgeType()));
        }
        else System.out.println("null list!!!");
    }

    @Test
  public void filterTypesTest(){

     List<Edge> edgeList =  edgeRepository.findAll();

     List<Edge> result = new ArrayList<>();

     Edge edge1 = apiService.filterEdgeByTypes(edgeList,RouteType.optimal);
        List<Edge> edgeList2 =  edgeRepository.findAll();

     Edge edge2 = apiService.filterEdgeByTypes(edgeList2,RouteType.cheap);
        List<Edge> edgeList3 =  edgeRepository.findAll();
     Edge edge3 = apiService.filterEdgeByTypes(edgeList3,RouteType.comfort);

     result.add(edge1);
     result.add(edge2);
     result.add(edge3);

     result.forEach(l-> System.out.println(l.getId()+" "+l.getEdgeType()+" "+l.getWeight()));


    }

    @Test
    public void go(){

      List<Edge> list = convertPointsToListEdges.findAll("Voronezh","Berlin");
        List<Edge> result = new ArrayList<>();

//      List<Edge> listCheap = list.stream().filter(l->l.getEdgeType()==RouteType.cheap).collect(Collectors.toList());
//        result.addAll(algorithm.getMinimalRoute(listCheap, "VOZ", "BER"));

      List<Edge> listOptimal = list.stream().filter(l->l.getEdgeType()==RouteType.optimal).collect(Collectors.toList());
        result.addAll(algorithm.getMinimalRoute(listOptimal, "VOZ", "BER"));

//      List<Edge> listComfort = list.stream().filter(l->l.getEdgeType()==RouteType.comfort).collect(Collectors.toList());
//        result.addAll(algorithm.getMinimalRoute(listComfort, "VOZ", "BER"));

//      algorithm.getMinimalRoute(listOptimal,"VOZ","BER").forEach(l->System.out.println(l.toString()));
//      algorithm.getMinimalRoute(listComfort,"VOZ","BER").forEach(l->System.out.println(l.toString()));

        edgeRepository.save(result);




    }

    @Test
    public void gogog(){

        String from = "Voronezh";
        String to = "Berlin";

        List<Edge> list = convertPointsToListEdges.findAll(from,to);

        List<Edge> list1 = RoutesFinalService.separator(list,RouteType.optimal);

        LinkedList<Edge> edges1 =(LinkedList<Edge>) algorithm.getMinimalRoute(list1,"VOZ","BER");

        Route route1 = new Route(new Date(),from,to,RouteType.optimal);

        for (Edge anEdges1 : edges1) {

            RouteEdge routeEdge = new RouteEdge(123);
            routeEdge.setRoute(route1);
            routeEdge.setEdge(anEdges1);
            route1.getRouteEdges().add(routeEdge);
            route1.setCost(+anEdges1.getCost());
        }


        edgeRepository.save(edges1);
        routeRepository.save(route1);

//
//        List<Edge> list2 = RoutesFinalService.separator(list,RouteType.cheap);
//
//        LinkedList<Edge> edges2 =(LinkedList<Edge>) algorithm.getMinimalRoute(list2,"VOZ","BER");
//
//        Route route2 = new Route(new Date(),from,to,RouteType.cheap);
//
//        for (Edge anEdges1 : edges2) {
//
//            RouteEdge routeEdge = new RouteEdge(456);
//            routeEdge.setRoute(route2);
//            routeEdge.setEdge(anEdges1);
//            route2.getRouteEdges().add(routeEdge);
//        }

//        edgeRepository.save(edges2);
//        routeRepository.save(route2);


//        List<Edge> list3 = RoutesFinalService.separator(list,RouteType.comfort);
//
//        LinkedList<Edge> edges3 =(LinkedList<Edge>) algorithm.getMinimalRoute(list3,"VOZ","BER");
//
//        Route route3 = new Route(new Date(),from,to,RouteType.comfort);
//
//        for (Edge anEdges1 : edges3) {
//
//            RouteEdge routeEdge = new RouteEdge(789);
//            routeEdge.setRoute(route3);
//            routeEdge.setEdge(anEdges1);
//            route3.getRouteEdges().add(routeEdge);
//        }

//        edgeRepository.save(edges3);
//        routeRepository.save(route3);


    }
    @Test
    public void gogogogog(){

        List<Edge> list = convertPointsToListEdges.findAll("Voronezh","Berlin");


        List<Route> list1 = routeFinder.findRoutes(list,"VOZ","BER");

        list1.forEach(l-> System.out.println(l.toString()));

        routeRepository.save(list1);


    }




}
