package com.netcracker.travelplanner.services;


import com.netcracker.travelplanner.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netcracker.travelplanner.model.entities.Edge;

import java.util.List;

@Service
public class EdgeRepositoryService {
    @Autowired
    private EdgeRepository edgeRepository;

    /**
     * save edge in database
     * @param edge - edge to be saved
     */
    public void save(Edge edge){edgeRepository.save(edge);}

    /**
     * save list of edges in database
     * @param list - list of edges to be saved
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
//    public List<Edge> findByStartPointAndDestinationPoint(String s, String d){
//        return edgeRepository.findByStartPointIsAndDestinationPointIs(s, d);
//    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of edges by start OR destination point
     */
//    public List<Edge> findByStartPointOrDestinationPoint(String s, String d){
//        return edgeRepository.findByStartPointIsOrDestinationPointIs(s, d);
//    }

    /**
     * @param d - duration of edges
     * @return list of edges by duration
     */
    public List<Edge> findByDuration(double d){
        return edgeRepository.findByDuration(d);
    }

    /**
     * @param c - cost of edges
     * @return list of edges by cost
     */
    public List<Edge> findByCost(double c){
        return edgeRepository.findByCost(c);
    }

    /**
     * @param type - transport type of edges
     * @return list of edges by transport type
     */
    public List<Edge> findByTransportType(String type){
        return edgeRepository.findByTransportType(type);
    }

    /**
     * delete all model managed by repository
     */
    public void deleteAll() {
        edgeRepository.deleteAll();
    }
}