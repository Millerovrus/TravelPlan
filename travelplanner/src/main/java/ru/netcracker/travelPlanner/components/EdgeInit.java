package ru.netcracker.travelPlanner.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.netcracker.travelPlanner.entities.Edge;
import ru.netcracker.travelPlanner.service.EdgeRepositoryService;
import ru.netcracker.travelPlanner.service.KiwiService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

//@Component
public class EdgeInit {

    @Autowired
    private EdgeRepositoryService edgeRepositoryService;

    @PostConstruct
    @Transactional
    public void init(){
//        KiwiService kiwiService = new KiwiService();
//
//        List<Edge> edges = kiwiService.getEdgesFlights();
//
//        edges.forEach(l -> System.out.println(l.toString()));
//
//        edgeRepositoryService.addAll(edges);
    }
}
