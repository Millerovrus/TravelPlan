package com.netcracker.travelplanner.api;

import java.time.LocalDate;

public class InitializatorApi {

    private static volatile InitializatorApi instance = null;

    private static String from, to;
    private static double latitudeFrom, latitudeTo, longitudeFrom, longitudeTo;
    private static LocalDate departureRoute, arrivalRoute;
    private static String iataCodeFrom, iataCodeTo;
    private static String yandexCodeFrom, yandexCodeTo;
    private static String closesCitiesFrom, closesCitiesTo;
    private static boolean isGlobalRoute;
    //private WebParser webParser;

    private InitializatorApi(String from,
                             String to,
                             double latitudeFrom,
                             double latitudeTo,
                             double longitudeFrom,
                             double longitudeTo,
                             LocalDate departureRoute,
                             LocalDate arrivalRoute,
                             String iataCodeFrom,
                             String iataCodeTo,
                             String yandexCodeFrom,
                             String yandexCodeTo,
                             String closesCitiesFrom,
                             String closesCitiesTo,
                             boolean isGlobalRoute){
        setFrom(from);
        setTo(to);
        setLatitudeFrom(latitudeFrom);
        setLatitudeTo(latitudeTo);
        setLongitudeFrom(longitudeFrom);
        setLongitudeTo(longitudeTo);
        setDepartureRoute(departureRoute);
        setArrivalRoute(arrivalRoute);
        setIataCodeFrom(iataCodeFrom);
        setIataCodeTo(iataCodeTo);
        setYandexCodeFrom(yandexCodeFrom);
        setYandexCodeTo(yandexCodeTo);
        setClosesCitiesFrom(closesCitiesFrom);
        setClosesCitiesTo(closesCitiesTo);
        setGlobalRoute(isGlobalRoute);

    }

    public static InitializatorApi getInstance(){
        if(instance==null){
            synchronized (InitializatorApi.class){
                if(instance==null){
                    instance = new InitializatorApi(from,
                                                    to,
                                                    latitudeFrom,
                                                    latitudeTo,
                                                    longitudeFrom,
                                                    longitudeTo,
                                                    departureRoute,
                                                    arrivalRoute,
                                                    iataCodeFrom,
                                                    iataCodeTo,
                                                    yandexCodeFrom,
                                                    yandexCodeTo,
                                                    closesCitiesFrom,
                                                    closesCitiesTo,
                                                    isGlobalRoute);
                }
            }
        }
        return instance;
    }


    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setLatitudeFrom(double latitudeFrom) {
        this.latitudeFrom = latitudeFrom;
    }

    public void setLatitudeTo(double latitudeTo) {
        this.latitudeTo = latitudeTo;
    }

    public void setLongitudeFrom(double longitudeFrom) {
        this.longitudeFrom = longitudeFrom;
    }

    public void setLongitudeTo(double longitudeTo) {
        this.longitudeTo = longitudeTo;
    }

    public void setDepartureRoute(LocalDate departureRoute) {
        this.departureRoute = departureRoute;
    }

    public void setArrivalRoute(LocalDate arrivalRoute) {
        this.arrivalRoute = arrivalRoute;
    }

    public void setIataCodeFrom(String iataCodeFrom) {
        this.iataCodeFrom = iataCodeFrom;
    }

    public void setIataCodeTo(String iataCodeTo) {
        this.iataCodeTo = iataCodeTo;
    }

    public void setYandexCodeFrom(String yandexCodeFrom) {
        this.yandexCodeFrom = yandexCodeFrom;
    }

    public void setYandexCodeTo(String yandexCodeTo) {
        this.yandexCodeTo = yandexCodeTo;
    }

    public void setClosesCitiesFrom(String closesCitiesFrom) {
        this.closesCitiesFrom = closesCitiesFrom;
    }

    public void setClosesCitiesTo(String closesCitiesTo) {
        this.closesCitiesTo = closesCitiesTo;
    }

    public void setGlobalRoute(boolean globalRoute) {
        isGlobalRoute = globalRoute;
    }
}
