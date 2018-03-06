package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Point;
import com.netcracker.travelplanner.entities.Edge;

import java.time.LocalDate;
import java.util.List;

public interface ApiInterface {
    List<Edge>  findEdgesFromTo(Point from, Point to, LocalDate date);

}
