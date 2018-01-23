package ru.netcracker.travelPlanner.entities;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class RouteEdgeId implements Serializable {

    @ManyToOne
    private Route route;

    @ManyToOne
    private Edge edge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteEdgeId that = (RouteEdgeId) o;

        return (route != null ? route.equals(that.route) : that.route == null) && (edge != null ? edge.equals(that.edge) : that.edge == null);
    }

    @Override
    public int hashCode() {
        int result;
        result = (route != null ? route.hashCode() : 0);
        result = 31 * result + (edge != null ? edge.hashCode() : 0);
        return result;
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
}
