package com.netcracker.travelplanner.entities;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDate;

public class Task {
    private Point from;
    private Point to;
    private LocalDate date;
    private int adultsCount;
    private int childrenCount;

    public Task(Point from, Point to, LocalDate date, int adultsCount, int childrenCount) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
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

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("from", from)
                .append("to", to)
                .append("date", date)
                .toString();
    }

}
