package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;

import java.time.LocalDate;
import java.util.List;

public interface ApiInterface {
    List<Edge>  findEdgesFromTo(Point from, Point to, LocalDate date);
    List<Edge>  findEdgesAllToOne(List<Point> pointListFrom, Point to, LocalDate date);
    List<Edge>  findEdgesOneToAll(Point from, List<Point> pointListTo,  LocalDate date);
    List<Edge>  findEdgesAllToAll(List<Point> pointListFrom, List<Point> pointListTo,  LocalDate date);
}
