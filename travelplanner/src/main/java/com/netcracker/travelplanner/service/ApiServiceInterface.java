package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;

import javax.ws.rs.DELETE;
import java.util.List;

@Deprecated
public interface ApiServiceInterface {
     List<Edge> foundEdges();
}
