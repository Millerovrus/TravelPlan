package com.netcracker.travelplanner.entities;

import java.util.*;

public class Way {
    private List<Edge> foundWay = new LinkedList<>();
    private double weight = 0;
    private boolean needDelete = false;

    public boolean isNeedDelete() {
        return needDelete;
    }

    public void setNeedDelete(boolean needDelete) {
        this.needDelete = needDelete;
    }

    public Way(Edge way, double weight) {
        this.foundWay.add(way);
        this.weight = weight;
    }

    public Way(List<Edge> tempWay, double weight) {
        this.foundWay = tempWay;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Way{" +
                "foundWay=" + foundWay +
                ", weight=" + weight +
                '}';
    }

    public List<Edge> getFoundWay() {
        return foundWay;
    }

    public double getWeight() {
        return weight;
    }
}