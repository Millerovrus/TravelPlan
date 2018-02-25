package com.netcracker.travelplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name="edges")
public class Edge implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edges_seq")
    @SequenceGenerator(name = "edges_seq", sequenceName = "edge_id_seq", allocationSize = 2)
    private int id;

    @Column(name="creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name="start_point", nullable = false)
    private String startPoint;

    @Column(name="destination_point", nullable = false)
    private String destinationPoint;

    @Column(name="transport_type", nullable = false)
    private String transportType;

    @Column(nullable = false)
    private Double duration;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "start_date", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "start_point_iata_code")
    private String startIataCode;

    @Column(name = "end_point_iata_code")
    private String endIataCode;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "edge_type")
    private RouteType edgeType;

    @Column(name = "edge_order")
    private Short edgeOrder;

    @Column(name = "latitude_from")
    private double latitudeFrom;

    @Column(name = "longitude_from")
    private double longitudeFrom;

    @Column(name = "latitude_to")
    private double latitudeTo;

    @Column(name = "longitude_to")
    private double longitudeTo;


    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route route;

    @Transient
    private double weight;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Short getEdgeOrder() {
        return edgeOrder;
    }

    public void setEdgeOrder(Short edgeOrder) {
        this.edgeOrder = edgeOrder;
    }

    public void setData(RouteType type){
        setEdgeType(type);

        switch (type){
            case cheap:
                setWeight(1.0, 50.0);
                break;

            case optimal:
                setWeight(1.0, 400.0);
                break;

            case comfort:
                setWeight(1.0, 1500.0);
                break;

            case cheapest:
                setWeight(1.0, 0.0000001);
                break;

            case fastest:
                setWeight(0.0000001, 10.0);
                break;

            default:
                break;
        }
    }

    public String getStartIataCode() {
        return startIataCode;
    }

    public void setStartIataCode(String startIataCode) {
        this.startIataCode = startIataCode;
    }

    public String getEndIataCode() {
        return endIataCode;
    }

    public void setEndIataCode(String endIataCode) {
        this.endIataCode = endIataCode;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    private void setWeight(double c1, double c2) {
        this.weight = c1 * cost + c2 * (duration / 3600);
    }

    public RouteType getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(RouteType edgeType) {
        this.edgeType = edgeType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getLatitudeFrom() {
        return latitudeFrom;
    }

    public void setLatitudeFrom(double latitudeFrom) {
        this.latitudeFrom = latitudeFrom;
    }

    public double getLongitudeFrom() {
        return longitudeFrom;
    }

    public void setLongitudeFrom(double longitudeFrom) {
        this.longitudeFrom = longitudeFrom;
    }

    public double getLatitudeTo() {
        return latitudeTo;
    }

    public void setLatitudeTo(double latitudeTo) {
        this.latitudeTo = latitudeTo;
    }

    public double getLongitudeTo() {
        return longitudeTo;
    }

    public void setLongitudeTo(double longitudeTo) {
        this.longitudeTo = longitudeTo;
    }


    public Edge(Date creationDate, String startPoint, String destinationPoint, String transportType, Double duration, Double cost, Double distance, LocalDateTime startDate, LocalDateTime endDate, String currency, RouteType edgeType)
    {
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.transportType = transportType;
        this.duration = duration;
        this.cost = cost;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
        this.edgeType = edgeType;
    }

    public Edge(Date creationDate, String startPoint, String destinationPoint, String transportType, Double duration, Double cost, Double distance, LocalDateTime startDate, LocalDateTime endDate, String currency, String startIataCode, String endIataCode) {
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.transportType = transportType;
        this.duration = duration;
        this.cost = cost;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
        this.startIataCode = startIataCode;
        this.endIataCode = endIataCode;
    }

    public Edge(Date creationDate, String startPoint, String destinationPoint, String transportType, Double duration, Double cost, Double distance, LocalDateTime startDate, LocalDateTime endDate, String currency, String startIataCode, String endIataCode, double latitudeFrom, double longitudeFrom, double latitudeTo, double longitudeTo) {
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.transportType = transportType;
        this.duration = duration;
        this.cost = cost;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
        this.startIataCode = startIataCode;
        this.endIataCode = endIataCode;
        this.latitudeFrom = latitudeFrom;
        this.longitudeFrom = longitudeFrom;
        this.latitudeTo = latitudeTo;
        this.longitudeTo = longitudeTo;
    }

    private Edge(){}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("creationDate", creationDate)
                .append("startPoint", startPoint)
                .append("destinationPoint", destinationPoint)
                .append("transportType", transportType)
                .append("duration", duration)
                .append("cost", cost)
                .append("distance", distance)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("currency", currency)
                .append("edgeType", edgeType)
                .append("weight", weight)
                .append("route", route)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return id == edge.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
