package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.EdgeRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EdgeApiController {
    @Autowired
    private EdgeRepositoryService edgeRepositoryService;

    @GetMapping("/getEdges")
    public Iterable<Edge> getEdges() {
        return edgeRepositoryService.getAllEdges();
    }
}
