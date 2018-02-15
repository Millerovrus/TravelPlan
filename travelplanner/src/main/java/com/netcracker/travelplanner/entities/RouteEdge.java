package com.netcracker.travelplanner.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "route_edges")
public class RouteEdge implements Serializable{

    @Id
    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonBackReference
    private Route route;

    @Id
    @ManyToOne
    @JoinColumn(name = "edge_id")
    @JsonBackReference
    private Edge edge;

    @Column(name = "edge_order", nullable = false)
    private int edgeOrder;

    public int getEdgeOrder() {
        return edgeOrder;
    }

    public void setEdgeOrder(int edgeOrder) {
        this.edgeOrder = edgeOrder;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public RouteEdge(int order){
        this.edgeOrder = order;
    }
    private RouteEdge(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RouteEdge routeEdge = (RouteEdge) o;

        return new EqualsBuilder()
                .append(edgeOrder, routeEdge.edgeOrder)
                .append(route, routeEdge.route)
                .append(edge, routeEdge.edge)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(route)
                .append(edge)
                .append(edgeOrder)
                .toHashCode();
    }
}
