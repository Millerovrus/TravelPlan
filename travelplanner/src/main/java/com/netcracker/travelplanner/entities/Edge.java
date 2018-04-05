package com.netcracker.travelplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date creationDate;

    @Column(name="transport_type", nullable = false)
    private String transportType;

    @Column(nullable = false)
    private Double duration;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "start_date", nullable = false)
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "edge_order")
    private Short edgeOrder;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route route;

    @Column(name = "number_of_transfers")
    private int numberOfTransfers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_point_id")
    private Point startPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_point_id")
    private Point endPoint;

    @OneToMany(mappedBy = "edge", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "transit_edges")
    private List<TransitEdge> transitEdgeList;

    @Column(name = "purchase_link", length = 1000)
    private String purchaseLink;

    public String getPurchaseLink() {
        return purchaseLink;
    }

    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Short getEdgeOrder() {
        return edgeOrder;
    }

    public void setEdgeOrder(Short edgeOrder) {
        this.edgeOrder = edgeOrder;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getNumberOfTransfers() {
        return numberOfTransfers;
    }

    public void setNumberOfTransfers(int numberOfTransfers) {
        this.numberOfTransfers = numberOfTransfers;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public List<TransitEdge> getTransitEdgeList() {
        return transitEdgeList;
    }

    public void setTransitEdgeList(List<TransitEdge> transitEdgeList) {
        this.transitEdgeList = transitEdgeList;
    }

    public Edge(Date creationDate
            , String transportType
            , Double duration
            , Double cost
            , LocalDateTime startDate
            , LocalDateTime endDate
            , String currency
            , int numberOfTransfers
            , Point startPointPoint
            , Point endPointPoint) {

        this.creationDate = creationDate;
        this.transportType = transportType;
        this.duration = duration;
        this.cost = cost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
        this.numberOfTransfers = numberOfTransfers;
        this.startPoint = startPointPoint;
        this.endPoint = endPointPoint;
    }

    public Edge(){}


    @Override
    public String toString() {
        return "Edge{" +
                "creationDate=" + creationDate +
                ", transportType='" + transportType + '\'' +
                ", duration=" + duration +
                ", cost=" + cost +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", currency='" + currency + '\'' +
                ", edgeOrder=" + edgeOrder +
                ", numberOfTransfers=" + numberOfTransfers +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", transitEdgeList=" + transitEdgeList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return numberOfTransfers == edge.numberOfTransfers &&
                Objects.equals(transportType, edge.transportType) &&
                Objects.equals(duration, edge.duration) &&
                Objects.equals(cost, edge.cost) &&
                Objects.equals(startDate, edge.startDate) &&
                Objects.equals(endDate, edge.endDate) &&
                Objects.equals(currency, edge.currency) &&
                Objects.equals(edgeOrder, edge.edgeOrder) &&
                Objects.equals(startPoint, edge.startPoint) &&
                Objects.equals(endPoint, edge.endPoint);
    }

    @Override
    public int hashCode() {

        return Objects.hash(transportType, duration, cost, startDate, endDate, currency, edgeOrder, numberOfTransfers, startPoint, endPoint);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}