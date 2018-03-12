package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PreparingDataService {

//     private InitializatorApi initializatorApi = InitializatorApi.getInstance();
     private static final Logger logger = LoggerFactory.getLogger(PreparingDataService.class);
    private Point pointFrom;

    private Point pointTo;

    public InitializatorApi prepareData(String from
            , String to
            , String latLongFrom
            , String latLongTo
            , String date){

        InitializatorApi initializatorApi = new InitializatorApi();

        pointFrom = new Point();
        pointTo = new Point();

        pointFrom.setName(from);
        pointTo.setName(to);

        String[] llfrom = latLongFrom.replaceAll("\\)","").replaceAll("\\(","").split(",");
        double latFrom = Double.parseDouble(llfrom[0]);
        double lonFrom = Double.parseDouble(llfrom[1]);

        pointFrom.setLatitude(latFrom);
        pointFrom.setLongitude(lonFrom);

        String[] llTo = latLongTo.replaceAll("\\)","").replaceAll("\\(","").split(",");
        double latTo = Double.parseDouble(llTo[0]);
        double lonTo = Double.parseDouble(llTo[1]);
        pointTo.setLatitude(latTo);
        pointTo.setLongitude(lonTo);

        initializatorApi.setDeparture(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE));

        pointFrom.setYandexCode(EdgeService.getYandexCode(latFrom, lonFrom));
        pointTo.setYandexCode(EdgeService.getYandexCode(latTo,lonTo));

        String iataCodeFrom = EdgeService.getIataCode(latFrom, lonFrom);
        String iataCodeTo = EdgeService.getIataCode(latTo, lonTo);

        pointFrom.setIataCode(iataCodeFrom);
        pointTo.setIataCode(iataCodeTo);

        if(EdgeService.isGlobalRoute(latFrom,lonFrom,latTo,lonTo)){

            initializatorApi.setGlobalRoute(true);
//             List<Point> tempFrom = EdgeService.getCities(iataCodeFrom, latFrom, lonFrom);
//             List<Point> tempTo = EdgeService.getCities(iataCodeTo, latTo, lonTo);
//             for (Point point : tempFrom) {
//                 if (point.getName().equals(to)){
//                     tempFrom.remove(point);
//                     break;
//                 }
//             }
//             for (Point point : tempTo) {
//                 if (point.getName().equals(from)){
//                     tempTo.remove(point);
//                     break;
//                 }
//             }
//             logger.debug("Точки в окружении {} {}", from, tempFrom.toString());
//             logger.debug("Точки в окружении {} {}", to, tempTo.toString());

//             initializatorApi.setCitiesFrom(tempFrom);
//             initializatorApi.setCitiesTo(tempTo);

            initializatorApi.setCitiesFrom(EdgeService.getCities(iataCodeFrom, latFrom, lonFrom));
            initializatorApi.setCitiesTo(EdgeService.getCities(iataCodeTo, latTo, lonTo));
        }
        else {
            initializatorApi.setGlobalRoute(false);
        }

        initializatorApi.setFrom(pointFrom);
        initializatorApi.setTo(pointTo);

        return initializatorApi;
    }

}
