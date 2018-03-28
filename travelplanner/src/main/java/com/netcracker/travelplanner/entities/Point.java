package com.netcracker.travelplanner.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Point {

    private String name;
    private double latitude;
    private double longitude;
    @JsonIgnore
    private String iataCode;
    @JsonIgnore
    private String yandexCode;
    @JsonIgnore
    private String locationCode;

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

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

    public Point(String name, double latitude, double longitude, String iataCode, String yandexCode, String locationCode) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iataCode = iataCode;
        this.yandexCode = yandexCode;
        this.locationCode = locationCode;
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return new EqualsBuilder()
                .append(latitude, point.latitude)
                .append(longitude, point.longitude)
                .append(name, point.name)
                .append(iataCode, point.iataCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(latitude)
                .append(longitude)
                .append(iataCode)
                .toHashCode();
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