package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.newKiwi.MyPoint;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public class InitializatorApi {

    private Point from;
    private Point to;
    private LocalDate departure;
    private LocalDate arrival;
    private boolean isGlobalRoute;
    private List<Point> citiesFrom;
    private List<Point> citiesTo;

    private static volatile InitializatorApi ourInstance = null;

    public static InitializatorApi getInstance() {
        if(ourInstance==null){
            synchronized (InitializatorApi.class){
                if(ourInstance==null){
                    ourInstance = new InitializatorApi();
                }
            }
        }
        return ourInstance;
    }

    private InitializatorApi() {
    }


    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
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

    public List<Point> getCitiesFrom() {
        return citiesFrom;
    }

    public void setCitiesFrom(List<Point> citiesFrom) {
        this.citiesFrom = citiesFrom;
    }

    public List<Point> getCitiesTo() {
        return citiesTo;
    }

    public void setCitiesTo(List<Point> citiesTo) {
        this.citiesTo = citiesTo;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("from", from)
                .append("to", to)
                .append("departure", departure)
                .append("arrival", arrival)
                .append("isGlobalRoute", isGlobalRoute)
                .append("citiesFrom", citiesFrom)
                .append("citiesTo", citiesTo)
                .toString();
    }
}
