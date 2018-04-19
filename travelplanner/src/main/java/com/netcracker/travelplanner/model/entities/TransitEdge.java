package com.netcracker.travelplanner.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.netcracker.travelplanner.model.JsonLocalDateTimeDeserializer;
import com.netcracker.travelplanner.model.JsonLocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transit_edges")
public class TransitEdge {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transits_points_seq")
    @SequenceGenerator(name = "transits_points_seq", sequenceName = "transit_point_id_seq", allocationSize = 2)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_point_id")
    @SerializedName("startPoint")
    @Expose
    private Point startPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "end_point_id")
    private Point endPoint;

    @Column(name = "end_date", nullable = false)
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    private LocalDateTime arrival;

    @Column(name = "start_date", nullable = false)
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    private LocalDateTime departure;

    @ManyToOne
    @JoinColumn(name = "edge_id")
    @JsonIgnore
    private Edge edge;

    public TransitEdge(Point startPoint, Point endPoint, LocalDateTime departure, LocalDateTime arrival) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.arrival = arrival;
        this.departure = departure;
    }

    private TransitEdge(){}

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    //просто строчка. оставлю тут.
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

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }
}