package com.netcracker.travelplanner.service;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netcracker.travelplanner.entities.Edge;

import java.util.ArrayList;
import java.util.List;

@Service
public class EdgeRepositoryService {

    @Autowired
    private EdgeRepository edgeRepository;

    /**
     * save edge in database
     * @param edge
     */
    public void save(Edge edge){edgeRepository.save(edge);}

    /**
     * save list of edges in database
     * @param list
     */
    public void save(List<Edge> list){
        edgeRepository.save(list);
    }

    /**
     * @return list of all edges
     */
    public List<Edge> findAll() {
        return edgeRepository.findAll();
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of edges by start AND destination point
     */
    public List<Edge> findByStartPointAndDestinationPoint(String s, String d){
        return edgeRepository.findByStartPointIsAndDestinationPointIs(s, d);
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of edges by start OR destination point
     */
    public List<Edge> findByStartPointOrDestinationPoint(String s, String d){
        return edgeRepository.findByStartPointIsOrDestinationPointIs(s, d);
    }

    /**
     * @param d
     * @return list of edges by distance
     */
    public List<Edge> findByDistance(double d){
        return edgeRepository.findByDistance(d);
    }

    /**
     * @param d
     * @return list of edges by duration
     */
    public List<Edge> findByDuration(double d){
        return edgeRepository.findByDuration(d);
    }

    /**
     * @param d
     * @return list of edges by cost
     */
    public List<Edge> findByCost(double d){
        return edgeRepository.findByCost(d);
    }

    /**
     * @param type
     * @return list of edges by transport type
     */
    public List<Edge> findByTransportType(String type){
        return edgeRepository.findByTransportType(type);
    }

    /**
     * @param type
     * @return list of edges by type
     */
    public List<Edge> findByEdgeType(RouteType type){
        return edgeRepository.findByEdgeType(type);
    }

    /**
     * delete all entities managed by repository
     */
    public void deleteAll() {
        edgeRepository.deleteAll();
    }
}
