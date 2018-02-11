package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/rest")
public class EdgeTestController {

    @Autowired
    private EdgeRepository edgeRepository;

    @RequestMapping(value = "/get-edges/", method = RequestMethod.GET)
    public List<Edge> getEdgeFromTo(@RequestParam("from") String from, @RequestParam("to") String to){
        return edgeRepository.findByStartPointIsAndDestinationPointIs(from, to);
    }
}
