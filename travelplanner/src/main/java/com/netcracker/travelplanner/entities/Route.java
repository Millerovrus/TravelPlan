package com.netcracker.travelplanner.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="routes")
public class Route implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routes_seq")
    @SequenceGenerator(name = "routes_seq", sequenceName = "route_id_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name="start_point", nullable = false)
    private String startPoint;

    @Column(name="destination_point", nullable = false)
    private String destinationPoint;

    @Column(name="route_type", nullable = false)
    private int routeType;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RouteEdge> routeEdges;

    public Set<RouteEdge> getRouteEdges() {
        return routeEdges;
    }

    public void setRouteEdges(Set<RouteEdge> routeEdges) {
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

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route(Date creationDate, String startPoint, String destinationPoint, int routeType) {
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.routeType = routeType;
        routeEdges = new HashSet<>();
    }
    private Route(){}
}
