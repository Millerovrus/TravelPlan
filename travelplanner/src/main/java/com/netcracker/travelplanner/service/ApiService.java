package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.entities.newKiwi.MyPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Deprecated
@Service
public class ApiService implements IntegrationAPIService {
    private static Logger logger = LoggerFactory.getLogger(ApiService.class);

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
    public List<Edge> getEdgesFromTo(String from, String to, LocalDate localDate) {
        logger.debug("Начало запросов к api для получения Edge, from: {} to: {}",from, to);
        List<Edge> edgeList = new ArrayList<>();
        List<Edge> result = new ArrayList<>();
        String codeFrom = cityToIataCode(from);
        String codeTo = cityToIataCode(to);

//        logger.debug("Запрос к yandex...");
        List<Edge> list1 = yandexService.getEdgesFromYandex(from
                ,to
                ,localDate
                ,codeFrom
                ,codeTo);
        if( list1!=null && !list1.isEmpty()){edgeList.addAll(list1);}

//        logger.debug("Запрос к kiwi...");
        List<Edge> list2 = kiwiService.getEdgesFlights(from
                ,to
                ,localDate
                ,localDate
                ,codeFrom
                ,codeTo );

        if( list2!=null && !list2.isEmpty()){edgeList.addAll(list2);}

        if (!edgeList.isEmpty()) {
            result.add(filterEdgeByTypes(edgeList, RouteType.cheap));
            result.add(filterEdgeByTypes(edgeList, RouteType.optimal));
            result.add(filterEdgeByTypes(edgeList, RouteType.comfort));
            result.add(filterEdgeByTypes(edgeList, RouteType.fastest));
            result.add(filterEdgeByTypes(edgeList, RouteType.cheapest));
        }
        return result;
    }

    /**
     * устанавливает тип ребра и фильтрация по типу
     * @param edgeList список Edge без типа
     * @param type тип ребра
     * @return Edge минимальный по установленному типу
     */
    private Edge filterEdgeByTypes(List<Edge> edgeList, RouteType type){

//        logger.debug("Установка типа ребра и фильтрация по типу: " + type.toString());
        Edge edge = null;
        edgeList.forEach(l->l.setEdgeType(type));
        edgeList.forEach(l->l.setData(type));
        try {
            edge = (Edge) edgeList.stream().min(Comparator.comparingDouble(Edge::getWeight)).get().clone();
//            logger.debug("OK!");
        } catch (CloneNotSupportedException e) {
            logger.error("Error!", e);
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

        logger.debug("Получение ближайших городов с аэропортами для города: {}", city);
        List<String> codes = new ArrayList<>();

        MyPoint airport = airportRepoService.getMyAirport(city);
        if(airport != null){
            double latitude = airport.getLat();
            double longitude = airport.getLon();

            List<MyPoint> myPointList =
            kiwiService.getAirportsByRadius(500,latitude,longitude);
            if(myPointList !=null){
               myPointList
                       .stream()
                       .filter(a -> !a.getCityName().equals(city))
                       .filter(distinctByKey(MyPoint::getCityCode))
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
     * функция для стрима, убирает дублирования элементов, сравнивая по полю
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
