package com.netcracker.travelplanner.entities;

import org.apache.commons.lang.builder.ToStringBuilder;
import java.time.LocalDate;
import java.util.List;


public class SearchInputParameters {

    private Point from;
    private Point to;
    private LocalDate departure;
    private LocalDate arrival;
    private boolean isGlobalRoute;
    private List<Point> citiesFrom;
    private List<Point> citiesTo;
    private int numberOfPassengers;

    public SearchInputParameters() {
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

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
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
                .append("numberOfPassengers", numberOfPassengers)
                .toString();
    }
}
