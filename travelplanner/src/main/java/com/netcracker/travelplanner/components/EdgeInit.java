package com.netcracker.travelplanner.components;


import com.netcracker.travelplanner.service.EdgeRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
@Deprecated
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
