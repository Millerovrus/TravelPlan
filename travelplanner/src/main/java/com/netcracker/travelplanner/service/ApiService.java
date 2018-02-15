package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.entities.newKiwi.MyAirport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;


@Service
public class ApiService implements IntegrationAPIService {

    @Autowired
    private AirportRepoService airportRepoService;

    @Autowired
    private KiwiService kiwiService;

    @Autowired
    private YandexService yandexService;


    /**
     * выполняет запросы в api для получения Edge
     * @param from название города отправления
     * @param to название города назначения
     * @return список ребер отфильтрованных по типу ребра cheap, optimal, comfort
     */
    public List<Edge> getEdgesFromTo(String from, String to) {

        List<Edge> edgeList = new ArrayList<>();
        List<Edge> result = new ArrayList<>();
        String codeFrom = cityToIataCode(from);
        String codeTo = cityToIataCode(to);
        edgeList.addAll(yandexService.getEdgesFromYandex(codeFrom
                ,codeTo
                ,LocalDate.of(2018,3,10)));

        edgeList.addAll(kiwiService.getEdgesFlights(codeFrom
                ,codeTo
                ,LocalDate.of(2018,3,10)
                ,LocalDate.of(2018,3,10)));

        if (!edgeList.isEmpty()) {
            result.add(filterEdgeByTypes(edgeList, RouteType.cheap));
            result.add(filterEdgeByTypes(edgeList, RouteType.optimal));
            result.add(filterEdgeByTypes(edgeList, RouteType.comfort));
        }
        return result;
    }

    /**
     * устанавливает тип ребра и фильтрация по типу
     * @param edgeList список Edge без типа
     * @param type тип ребра
     * @return Edge минимальный по установленному типу
     */
    public Edge filterEdgeByTypes(List<Edge> edgeList, RouteType type){

        Edge edge = null;
        edgeList.forEach(l->l.setEdgeType(type));
        edgeList.forEach(l->l.setData(type));
        try {
            edge = (Edge) edgeList.stream().min(Comparator.comparingDouble(Edge::getWeight)).get().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return edge;
    }

    /**
     * метод для получения ближайшик городов с аэропортами
     * @param city название города на английском языке
     * @return список городов в кодах IATA
     */
    public List<String> getClosesCities(String city) {

        List<String> codes = new ArrayList<>();

        MyAirport airport = airportRepoService.getMyAirport(city);
        if(airport != null){
            double latitude = airport.getLat();
            double longitude = airport.getLon();

            List<MyAirport> myAirportList =
            kiwiService.getAirportsByRadius(500,latitude,longitude);
            if(myAirportList!=null){
               myAirportList
                       .stream()
                       .filter(a -> !a.getCityName().equals(city))
                       .filter(distinctByKey(MyAirport::getCityCode))
                       .limit(5)
                       .forEach(l -> codes.add(l.getCityName()));
            }
        }
        return codes;
    }


    /** ищет код IATA по имени города
     * @param city
     * @return строка с кодом IATA города
     */
    public String cityToIataCode(String city){
        return airportRepoService.getIataCode(city);
    }


    /**
     * функция для стрима, оставляет уникальные элементы сравнивая по полю
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


}
