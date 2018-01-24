package com.netcracker.travelplanner.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "route_edges")
@AssociationOverrides({@AssociationOverride(name = "pk.route", joinColumns = @JoinColumn(name = "route_id")),
                       @AssociationOverride(name = "pk.edge", joinColumns = @JoinColumn(name = "edge_id"))})
public class RouteEdge implements Serializable{
    @EmbeddedId
    private RouteEdgeId primaryKey;

    @Column(name = "edge_order", nullable = false)
    private Short edgeOrder;

    public RouteEdge() {
    }

    public RouteEdgeId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(RouteEdgeId primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Short getEdgeOrder() {
        return edgeOrder;
    }

    public void setEdgeOrder(Short edgeOrder) {
        this.edgeOrder = edgeOrder;
    }
}
