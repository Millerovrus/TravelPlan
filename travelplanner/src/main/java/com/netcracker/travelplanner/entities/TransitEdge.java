package com.netcracker.travelplanner.entities;

import java.time.LocalDateTime;

public class TransitEdge {

    private Point startPoint;
    private Point endPoint;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    public TransitEdge(Point startPoint, Point endPoint, LocalDateTime arrival, LocalDateTime departure) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.arrival = arrival;
        this.departure = departure;
    }

    //просто строчка. оставлю тут.
    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }
}