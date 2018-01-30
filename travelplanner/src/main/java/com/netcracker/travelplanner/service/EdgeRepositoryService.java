package com.netcracker.travelplanner.service;


import com.netcracker.travelplanner.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netcracker.travelplanner.entities.Edge;

import java.util.List;

@Service
public class EdgeRepositoryService {

    @Autowired
    private EdgeRepository edgeRepository;

    public void addAll(List<Edge> list){
        edgeRepository.save(list);
    }

    public Iterable<Edge> getEdges() {
        return edgeRepository.findAll();
    }

}
