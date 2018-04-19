package com.netcracker.travelplanner.model;

import com.netcracker.travelplanner.model.entities.Point;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDate;

public class Task {
    private Point from;
    private Point to;
    private LocalDate date;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfInfants;

    public Task(Point from, Point to, LocalDate date, int numberOfAdults, int numberOfChildren, int numberOfInfants) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.numberOfInfants = numberOfInfants;
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

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public int getNumberOfInfants() {
        return numberOfInfants;
    }

    public void setNumberOfInfants(int numberOfInfants) {
        this.numberOfInfants = numberOfInfants;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("from", from)
                .append("to", to)
                .append("date", date)
                .append("numberOfAdults", numberOfAdults)
                .append("numberOfChildren", numberOfChildren)
                .append("numberOfInfants", numberOfInfants)
                .toString();
    }
}