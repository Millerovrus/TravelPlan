package com.netcracker.travelplanner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="routes")
public class Route implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routes_seq")
    @SequenceGenerator(name = "routes_seq", sequenceName = "route_id_seq", allocationSize = 2)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name="creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name="start_point", nullable = false)
    private String startPoint;

    @Column(name="destination_point", nullable = false)
    private String destinationPoint;

    @Transient
    private List<Double> weights;

    @Transient
    private boolean isOptimalRoute = false;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "duration", nullable = false)
    private double duration;

    @Column(name = "distance")
    private double distance;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edge> edges;

    @Transient
    private int idRouteForView;

    public int getIdRouteForView() {
        return idRouteForView;
    }

    public void setIdRouteForView(int idRouteForView) {
        this.idRouteForView = idRouteForView;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public boolean isOptimalRoute() {
        return isOptimalRoute;
    }

    public void setOptimalRoute(boolean optimalRoute) {
        isOptimalRoute = optimalRoute;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route(Date creationDate, String startPoint, String destinationPoint, double duration, int idRouteForView) {
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.duration = duration;
        this.idRouteForView = idRouteForView;
        this.edges = new LinkedList<>();
        this.weights = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (id != route.id) return false;
        if (Double.compare(route.cost, cost) != 0) return false;
        if (Double.compare(route.duration, duration) != 0) return false;
        if (Double.compare(route.distance, distance) != 0) return false;
        if (idRouteForView != route.idRouteForView) return false;
        if (user != null ? !user.equals(route.user) : route.user != null) return false;
        if (!creationDate.equals(route.creationDate)) return false;
        if (!startPoint.equals(route.startPoint)) return false;
        if (!destinationPoint.equals(route.destinationPoint)) return false;
        if (!weights.equals(route.weights)) return false;
        return edges.equals(route.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("user", user)
                .append("creationDate", creationDate)
                .append("startPoint", startPoint)
                .append("destinationPoint", destinationPoint)
                .append("weights", weights.toString())
                .append("cost", cost)
                .append("duration", duration)
                .append("distance", distance)
                .append("isOptimalRoute", isOptimalRoute)
                .append("edges", edges.toString())
                .toString();
    }


}