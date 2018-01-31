package com.netcracker.travelplanner.entities;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "route_edges")
public class RouteEdge implements Serializable{

    @Id
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Id
    @ManyToOne
    @JoinColumn(name = "edge_id")
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
}
