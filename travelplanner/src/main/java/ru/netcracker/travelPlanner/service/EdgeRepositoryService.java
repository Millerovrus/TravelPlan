package ru.netcracker.travelPlanner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netcracker.travelPlanner.entities.Edge;
import ru.netcracker.travelPlanner.repository.EdgeRepository;

import java.util.List;

@Service
public class EdgeRepositoryService {

    @Autowired
    private EdgeRepository edgeRepository;

    public void addAll(List<Edge> list){
        edgeRepository.save(list);
    }

}
