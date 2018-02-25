package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.newKiwi.MyPoint;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.util.List;

public class InitializatorApi {

    private String from;
    private String to;
    private double latitudeFrom;
    private double longitudeFrom;
    private double latitudeTo;
    private double longitudeTo;
    private String iataCodeFrom;
    private String iataCodeTo;
    private String yandexCodeFrom;
    private String yandexCodeTo;
    private LocalDate departure;
    private LocalDate arrival;
    private boolean isGlobalRoute;
    private List<MyPoint> citiesFrom;
    private List<MyPoint> citiesTo;
    private WebDriver webDriver;

    private static InitializatorApi ourInstance = new InitializatorApi();

    public static InitializatorApi getInstance() {
        return ourInstance;
    }

    private InitializatorApi() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getLatitudeFrom() {
        return latitudeFrom;
    }

    public void setLatitudeFrom(double latitudeFrom) {
        this.latitudeFrom = latitudeFrom;
    }

    public double getLongitudeFrom() {
        return longitudeFrom;
    }

    public void setLongitudeFrom(double longitudeFrom) {
        this.longitudeFrom = longitudeFrom;
    }

    public double getLatitudeTo() {
        return latitudeTo;
    }

    public void setLatitudeTo(double latitudeTo) {
        this.latitudeTo = latitudeTo;
    }

    public double getLongitudeTo() {
        return longitudeTo;
    }

    public void setLongitudeTo(double longitudeTo) {
        this.longitudeTo = longitudeTo;
    }

    public String getIataCodeFrom() {
        return iataCodeFrom;
    }

    public void setIataCodeFrom(String iataCodeFrom) {
        this.iataCodeFrom = iataCodeFrom;
    }

    public String getIataCodeTo() {
        return iataCodeTo;
    }

    public void setIataCodeTo(String iataCodeTo) {
        this.iataCodeTo = iataCodeTo;
    }

    public String getYandexCodeFrom() {
        return yandexCodeFrom;
    }

    public void setYandexCodeFrom(String yandexCodeFrom) {
        this.yandexCodeFrom = yandexCodeFrom;
    }

    public String getYandexCodeTo() {
        return yandexCodeTo;
    }

    public void setYandexCodeTo(String yandexCodeTo) {
        this.yandexCodeTo = yandexCodeTo;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public boolean isGlobalRoute() {
        return isGlobalRoute;
    }

    public void setGlobalRoute(boolean globalRoute) {
        isGlobalRoute = globalRoute;
    }

    public List<MyPoint> getCitiesFrom() {
        return citiesFrom;
    }

    public void setCitiesFrom(List<MyPoint> citiesFrom) {
        this.citiesFrom = citiesFrom;
    }

    public List<MyPoint> getCitiesTo() {
        return citiesTo;
    }

    public void setCitiesTo(List<MyPoint> citiesTo) {
        this.citiesTo = citiesTo;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("from", from)
                .append("to", to)
                .append("latitudeFrom", latitudeFrom)
                .append("longitudeFrom", longitudeFrom)
                .append("latitudeTo", latitudeTo)
                .append("longitudeTo", longitudeTo)
                .append("iataCodeFrom", iataCodeFrom)
                .append("iataCodeTo", iataCodeTo)
                .append("yandexCodeFrom", yandexCodeFrom)
                .append("yandexCodeTo", yandexCodeTo)
                .append("departure", departure)
                .append("arrival", arrival)
                .append("isGlobalRoute", isGlobalRoute)
                .append("citiesFrom", citiesFrom)
                .append("citiesTo", citiesTo)
                .toString();
    }
}
