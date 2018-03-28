package com.netcracker.travelplanner.entities;


import org.apache.commons.lang.builder.ToStringBuilder;

public class Point {

    private String name;
    private double latitude;
    private double longitude;
    private String iataCode;
    private String yandexCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getYandexCode() {
        return yandexCode;
    }

    public void setYandexCode(String yandexCode) {
        this.yandexCode = yandexCode;
    }

    public Point(String name, double latitude, double longitude, String iataCode, String yandexCode) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iataCode = iataCode;
        this.yandexCode = yandexCode;
    }

    public Point(String name, double latitude, double longitude, String iataCode) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iataCode = iataCode;
    }

    public Point() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .append("iataCode", iataCode)
                .append("yandexCode", yandexCode)
                .toString();
    }
}
