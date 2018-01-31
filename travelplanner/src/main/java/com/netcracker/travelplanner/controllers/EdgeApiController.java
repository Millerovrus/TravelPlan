package com.netcracker.travelplanner.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.service.EdgeRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EdgeApiController {
    @Autowired
    private EdgeRepositoryService edgeRepositoryService;

    @GetMapping("/getEdges")
    public List<Edge> getEdges() {
        return edgeRepositoryService.getAllEdges();
    }
}
