package com.netcracker.travelplanner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.*;


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
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "start_point_iata_code")
    private String startIataCode;

    @Column(name = "end_point_iata_code")
    private String endIataCode;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "edge_type")
    private RouteType edgeType;

    @OneToMany(mappedBy = "edge", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<RouteEdge> routeEdges;

    @Transient
    private double weight;

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
                setWeight(1.0, 0.000001);
                break;

            case fastest:
                setWeight(0.000001, 1.0);
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

    public void setWeight(double c1, double c2) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<RouteEdge> getRouteEdges() {
        return routeEdges;
    }

    public void setRouteEdges(Set<RouteEdge> routeEdges) {
        this.routeEdges = routeEdges;
    }

    public Edge(Date creationDate, String startPoint, String destinationPoint, String transportType, Double duration, Double cost, Double distance, Date startDate, Date endDate, String currency, RouteType edgeType)
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
        routeEdges = new HashSet<>();
    }

    public Edge(Date creationDate, String startPoint, String destinationPoint, String transportType, Double duration, Double cost, Double distance, Date startDate, Date endDate, String currency, String startIataCode, String endIataCode) {
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
                .append("routeEdges", routeEdges)
                .append("weight", weight)
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
