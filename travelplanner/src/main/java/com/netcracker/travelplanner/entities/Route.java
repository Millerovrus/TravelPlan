package com.netcracker.travelplanner.entities;

import org.postgresql.geometric.PGpoint;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="routes")
public class Route implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routes_seq")
    @SequenceGenerator(name = "routes_seq", sequenceName = "route_id_seq")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name="start_point", nullable = false)
    private PGpoint startPoint;

    @Column(name="destination_point", nullable = false)
    private PGpoint destinationPoint;

    @Column(name="route_type", nullable = false)
    private Short routeType;

   /* @OneToMany(mappedBy = "pk.route")
    private Set<RouteEdge> routeEdges;

    public Set<RouteEdge> getRouteEdges() {
        return routeEdges;
    }

    public void setRouteEdges(Set<RouteEdge> routeEdges) {
        this.routeEdges = routeEdges;
    }*/

    public Route() {
    }

    public Route(Date creationDate, PGpoint startPoint, PGpoint destinationPoint, Short routeType) {
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.routeType = routeType;
    }

    public Route(User user, Date creationDate, PGpoint startPoint, PGpoint destinationPoint, Short routeType) {
        this.user = user;
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.routeType = routeType;
    }

    public Route(User user, Date creationDate, PGpoint startPoint, PGpoint destinationPoint, Short routeType, Set<RouteEdge> routeEdges) {
        this.user = user;
        this.creationDate = creationDate;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.routeType = routeType;
        //this.routeEdges = routeEdges;
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

    public PGpoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(PGpoint startPoint) {
        this.startPoint = startPoint;
    }

    public PGpoint getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(PGpoint destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public Short getRouteType() {
        return routeType;
    }

    public void setRouteType(Short routeType) {
        this.routeType = routeType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
