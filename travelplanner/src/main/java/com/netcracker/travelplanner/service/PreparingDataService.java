package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PreparingDataService {

    private static final Logger logger = LoggerFactory.getLogger(PreparingDataService.class);

    public InitializatorApi prepareData(String from
            , String to
            , String latLongFrom
            , String latLongTo
            , String date
            , int numberOfAdults
            , int numberOfChildren){

        InitializatorApi initializatorApi = new InitializatorApi();

        Point pointFrom = new Point();
        Point pointTo = new Point();

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

            List<Point> citiesFrom = EdgeService.getCities(iataCodeFrom, latFrom, lonFrom);
            List<Point> citiesTo = EdgeService.getCities(iataCodeTo, latTo, lonTo);
            int citiesToSize = citiesTo.size();

            //удаляем одинаковые города из списков, если они есть
            for (int i = 0; i < citiesToSize; i++) {
                for (Point pFrom : citiesFrom) {
                    if (citiesTo.get(i).getName().equals(pFrom.getName())){
                        citiesTo.remove(i);
                        i--;
                        citiesToSize--;
                    }
                }
            }
            //удаляем из окруженя стартовой точки конечную
            for (int i = 0; i < citiesFrom.size(); i++) {
                if (citiesFrom.get(i).getName().equals(to)){
                    citiesFrom.remove(i);
                    break;
                }
            }
            //и наоборот
            for (int i = 0; i < citiesTo.size(); i++) {
                if (citiesTo.get(i).getName().equals(from)){
                    citiesTo.remove(i);
                    break;
                }
            }
            logger.debug("{} cities around {}: {}", citiesFrom.size(), from, citiesFrom.toString());
            logger.debug("{} cities around {}: {}", citiesTo.size(), to, citiesTo.toString());

            initializatorApi.setCitiesFrom(citiesFrom);
            initializatorApi.setCitiesTo(citiesTo);

        }
        else {
            initializatorApi.setGlobalRoute(false);
        }

        initializatorApi.setFrom(pointFrom);
        initializatorApi.setTo(pointTo);
        initializatorApi.setNumberOfAdults(numberOfAdults);
        initializatorApi.setNumberOfChildren(numberOfChildren);

        return initializatorApi;
    }

}