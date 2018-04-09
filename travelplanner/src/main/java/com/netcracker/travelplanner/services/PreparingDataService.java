package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.models.entities.Point;
import com.netcracker.travelplanner.models.SearchInputParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class PreparingDataService {

    private static final Logger logger = LoggerFactory.getLogger(PreparingDataService.class);

    public SearchInputParameters prepareData(String from
            , String to
            , String latLongFrom
            , String latLongTo
            , String date
            , int numberOfPassengers){

        SearchInputParameters searchInputParameters = new SearchInputParameters();

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

        searchInputParameters.setDeparture(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE));

        pointFrom.setYandexCode(EdgeService.getYandexCode(latFrom, lonFrom));
        pointTo.setYandexCode(EdgeService.getYandexCode(latTo,lonTo));

        String iataCodeFrom = EdgeService.getIataCode(latFrom, lonFrom);
        String iataCodeTo = EdgeService.getIataCode(latTo, lonTo);

        pointFrom.setIataCode(iataCodeFrom);
        pointTo.setIataCode(iataCodeTo);

        if(EdgeService.isGlobalRoute(latFrom,lonFrom,latTo,lonTo)){

            searchInputParameters.setGlobalRoute(true);

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
                        break;
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

            searchInputParameters.setCitiesFrom(citiesFrom);
            searchInputParameters.setCitiesTo(citiesTo);

        }
        else {
            searchInputParameters.setGlobalRoute(false);
        }

        searchInputParameters.setFrom(pointFrom);
        searchInputParameters.setTo(pointTo);
        searchInputParameters.setNumberOfPassengers(numberOfPassengers);

        return searchInputParameters;
    }

}