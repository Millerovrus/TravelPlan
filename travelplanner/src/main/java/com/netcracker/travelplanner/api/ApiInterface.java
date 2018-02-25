package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import java.util.List;

public interface ApiInterface {
    List<Edge>  findEdgesFromTo();
    List<Edge>  findEdgesAllToOne();
    List<Edge>  findEdgesOneToAll();
    List<Edge>  findEdgesAllToAll();
}
