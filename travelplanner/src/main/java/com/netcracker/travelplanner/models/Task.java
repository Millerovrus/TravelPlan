package com.netcracker.travelplanner.models;

import com.netcracker.travelplanner.models.entities.Point;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDate;

public class Task {
    private Point from;
    private Point to;
    private LocalDate date;
    private int numberOfPassengers;

    public Task(Point from, Point to, LocalDate date, int numberOfPassengers) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.numberOfPassengers = numberOfPassengers;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                .append("date", date)
                .append("numberOfPassengers", numberOfPassengers)
                .toString();
    }
}