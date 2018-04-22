package com.netcracker.travelplanner.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netcracker.travelplanner.model.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Transient
    private List<TrainTicketsInfo> trainTicketsInfoList;

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

    public List<TrainTicketsInfo> getTrainTicketsInfoList() {
        return trainTicketsInfoList;
    }

    public void setTrainTicketsInfoList(List<TrainTicketsInfo> trainTicketsInfoList) {
        this.trainTicketsInfoList = trainTicketsInfoList;
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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("creationDate", creationDate)
                .append("transportType", transportType)
                .append("duration", duration)
                .append("cost", cost)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("currency", currency)
                .append("edgeOrder", edgeOrder)
                .append("route", route)
                .append("numberOfTransfers", numberOfTransfers)
                .append("startPoint", startPoint)
                .append("endPoint", endPoint)
                .append("transitEdgeList", transitEdgeList)
                .append("purchaseLink", purchaseLink)
                .append("trainTicketsInfoList", trainTicketsInfoList)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        return new EqualsBuilder()
                .append(id, edge.id)
                .append(numberOfTransfers, edge.numberOfTransfers)
                .append(creationDate, edge.creationDate)
                .append(transportType, edge.transportType)
                .append(duration, edge.duration)
                .append(cost, edge.cost)
                .append(startDate, edge.startDate)
                .append(endDate, edge.endDate)
                .append(currency, edge.currency)
                .append(edgeOrder, edge.edgeOrder)
                .append(route, edge.route)
                .append(startPoint, edge.startPoint)
                .append(endPoint, edge.endPoint)
                .append(transitEdgeList, edge.transitEdgeList)
                .append(purchaseLink, edge.purchaseLink)
                .append(trainTicketsInfoList, edge.trainTicketsInfoList)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(creationDate)
                .append(transportType)
                .append(duration)
                .append(cost)
                .append(startDate)
                .append(endDate)
                .append(currency)
                .append(edgeOrder)
                .append(route)
                .append(numberOfTransfers)
                .append(startPoint)
                .append(endPoint)
                .append(transitEdgeList)
                .append(purchaseLink)
                .append(trainTicketsInfoList)
                .toHashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}