package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.models.entities.TransitEdge;
import com.netcracker.travelplanner.repository.TransitEdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitEdgeRepositoryService {
    @Autowired
    private TransitEdgeRepository transitEdgeRepository;

    public void save(TransitEdge transitEdge){
        transitEdgeRepository.save(transitEdge);
    }

    public void saveAll(Iterable<TransitEdge> transitEdges){
        transitEdgeRepository.save(transitEdges);
    }

}
