package com.netcracker.travelplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="edges")
public class Edge {

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

    private Double cost;

    private Double distance;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    private String currency;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "edge_type")
    private RouteType edgeType;

    @OneToMany(mappedBy = "edge", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
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
                setWeight(1.0, 0.0);
                break;

            case fastest:
                setWeight(0.0, 1.0);
                break;

            default:
                break;
        }
    }

    public Set<RouteEdge> getRouteEdges() {
        return routeEdges;
    }

    public void setRouteEdges(Set<RouteEdge> routeEdges) {
        this.routeEdges = routeEdges;
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

    public Set<RouteEdge> getEdges() {
            return routeEdges;
    }

    public void setEdges(Set<RouteEdge> edges) {
        this.routeEdges = routeEdges;
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
    private Edge(){}

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", startPoint='" + startPoint + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", transportType=" + transportType +
                ", duration=" + duration +
                ", cost=" + cost +
                ", distance=" + distance +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", currency='" + currency + '\'' +
                '}';
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
}
