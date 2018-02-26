package com.netcracker.travelplanner.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PreparingDataService {

    private InitializatorApi initializatorApi = InitializatorApi.getInstance();

    public InitializatorApi prepareData(String from
            , String to
            , String latLongFrom
            , String latLongTo
            , String date){

        initializatorApi.setFrom(from);
        initializatorApi.setTo(to);

        String[] llfrom = latLongFrom.replaceAll("\\)","").replaceAll("\\(","").split(",");
        double latFrom = Double.parseDouble(llfrom[0]);
        double lonFrom = Double.parseDouble(llfrom[1]);

        initializatorApi.setLatitudeFrom(latFrom);
        initializatorApi.setLongitudeFrom(lonFrom);

        String[] llTo = latLongTo.replaceAll("\\)","").replaceAll("\\(","").split(",");
        double latTo = Double.parseDouble(llTo[0]);
        double lonTo = Double.parseDouble(llTo[1]);
        initializatorApi.setLatitudeTo(latTo);
        initializatorApi.setLongitudeTo(lonTo);

        initializatorApi.setDeparture(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE));

        initializatorApi.setYandexCodeFrom(EdgeService.getYandexCode(latFrom, lonFrom));
        initializatorApi.setYandexCodeTo(EdgeService.getYandexCode(latTo, lonTo));

        String iataCodeFrom = EdgeService.getIataCode(latFrom, lonFrom);
        String iataCodeTo = EdgeService.getIataCode(latTo, lonTo);
        initializatorApi.setIataCodeFrom(iataCodeFrom);
        initializatorApi.setIataCodeTo(iataCodeTo);

        if(EdgeService.isGlobalRoute(latFrom,lonFrom,latTo,lonTo)){
            initializatorApi.setGlobalRoute(true);

            initializatorApi.setCitiesFrom(EdgeService.getCities(iataCodeFrom, latFrom, lonFrom));
            initializatorApi.setCitiesTo(EdgeService.getCities(iataCodeTo, latTo, lonTo));

        }
        else {
            initializatorApi.setGlobalRoute(false);
        }
        return this.initializatorApi;
    }

}
