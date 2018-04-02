package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.TransitEdge;
import com.netcracker.travelplanner.repository.TransitEdgesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitEdgeRepositoryService {
    @Autowired
    private TransitEdgesRepository transitEdgesRepository;

    public void save(TransitEdge transitEdge){
        transitEdgesRepository.save(transitEdge);
    }

    public void saveAll(Iterable<TransitEdge> transitEdges){
        transitEdgesRepository.save(transitEdges);
    }

}
