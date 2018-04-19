package com.netcracker.travelplanner.model.entities;

import javax.persistence.Transient;

public class TrainTicketsInfo {
    @Transient
    private String wagonType;
    @Transient
    private Double cost;
    @Transient
    private Integer availableSeats;

    public String getWagonType() {
        return wagonType;
    }

    public void setWagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public TrainTicketsInfo(String wagonType, Double cost, Integer availableSeats) {
        this.wagonType = wagonType;
        this.cost = cost;
        this.availableSeats = availableSeats;
    }
}
