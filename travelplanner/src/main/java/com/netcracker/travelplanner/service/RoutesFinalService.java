package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.algorithms.Algorithm;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.RouteType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RoutesFinalService {

    public static List<Edge> separator(List<Edge> edges, RouteType type){

        List<Edge> edgeList = new ArrayList<>();
        edges.stream().filter(l->l.getEdgeType()== type).forEach(l -> {
            try {
                edgeList.add((Edge) l.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        return edgeList;
    }


}
