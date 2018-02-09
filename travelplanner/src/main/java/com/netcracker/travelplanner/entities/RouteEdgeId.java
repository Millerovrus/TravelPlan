package com.netcracker.travelplanner.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Deprecated
@Embeddable
public class RouteEdgeId implements Serializable {

    @Column(name = "route_id")
    private int routeId;

    @Column(name = "edge_id")
    private int edgeId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RouteEdgeId that = (RouteEdgeId) o;
        return Objects.equals(routeId, that.routeId) &&
                Objects.equals(edgeId, that.edgeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, edgeId);
    }

    private RouteEdgeId(){}
    public RouteEdgeId(int routeId, int edgeId) {
        this.routeId = routeId;
        this.edgeId = edgeId;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        RouteEdgeId that = (RouteEdgeId) o;
//
//        return (routeId != 0 ? routeId = that.routeId) : that.routeId == 0) && (edgeId != null ? edgeId.equals(that.edgeId) : that.edgeId == null);
//    }
//
//    @Override
//    public int hashCode() {
//        int result;
//        result = (route != null ? route.hashCode() : 0);
//        result = 31 * result + (edge != null ? edge.hashCode() : 0);
//        return result;
//    }


}
