package com.netcracker.travelplanner.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "points_seq")
    @SequenceGenerator(name = "points_seq", sequenceName = "point_id_seq", allocationSize = 2)
    private int id;

    @Column(name="name")
    @SerializedName("name")
    @Expose
    private String name;

    @Column(name="latitude")
    @SerializedName("latitude")
    @Expose
    private double latitude;

    @Column(name="longitude")
    @SerializedName("longitude")
    @Expose
    private double longitude;

    @Column(name="iata_code")
    @SerializedName("iataCode")
    @Expose
    private String iataCode;

    @Column(name="yandex_code")
    @SerializedName("yandexCode")
    @Expose
    private String yandexCode;

    @Column(name="location_code")
    @SerializedName("locationCode")
    @Expose
    private String locationCode;


    @OneToMany(mappedBy = "startPoint", orphanRemoval = true)
    @JsonIgnore
    private List<Edge> edgesStart;

    @OneToMany(mappedBy = "endPoint", orphanRemoval = true)
    @JsonIgnore
    private List<Edge> edgesEnd;

    @OneToMany(mappedBy = "startPoint", orphanRemoval = true)
    @JsonIgnore
    private List<TransitEdge> transitEdgesStart;

    @OneToMany(mappedBy = "endPoint", orphanRemoval = true)
    @JsonIgnore
    private List<TransitEdge> transitEdgesEnd;


    public List<Edge> getEdgesStart() {
        return edgesStart;
    }

    public void setEdgesStart(List<Edge> edgesStart) {
        this.edgesStart = edgesStart;
    }

    public List<Edge> getEdgesEnd() {
        return edgesEnd;
    }

    public void setEdgesEnd(List<Edge> edgesEnd) {
        this.edgesEnd = edgesEnd;
    }

    public List<TransitEdge> getTransitEdgesStart() {
        return transitEdgesStart;
    }

    public void setTransitEdgesStart(List<TransitEdge> transitEdgesStart) {
        this.transitEdgesStart = transitEdgesStart;
    }

    public List<TransitEdge> getTransitEdgesEnd() {
        return transitEdgesEnd;
    }

    public void setTransitEdgesEnd(List<TransitEdge> transitEdgesEnd) {
        this.transitEdgesEnd = transitEdgesEnd;
    }

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

    public Point(String name
            , double latitude
            , double longitude
            , String iataCode
            , String yandexCode
            , String locationCode) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.iataCode = iataCode;
        this.yandexCode = yandexCode;
        this.locationCode = locationCode;
        this.edgesStart = new ArrayList<>();
        this.edgesEnd = new ArrayList<>();
        this.transitEdgesEnd = new ArrayList<>();
        this.transitEdgesStart = new ArrayList<>();
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