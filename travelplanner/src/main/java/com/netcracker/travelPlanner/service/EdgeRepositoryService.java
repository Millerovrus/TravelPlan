package com.netcracker.travelPlanner.service;


import com.netcracker.travelPlanner.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netcracker.travelPlanner.entities.Edge;

import java.util.List;

@Service
public class EdgeRepositoryService {

    @Autowired
    private EdgeRepository edgeRepository;

    public void addAll(List<Edge> list){
        edgeRepository.save(list);
    }

}
