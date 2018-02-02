package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/getEdges")
public class EdgeApiController {
    @Autowired
    private EdgeRepository edgeRepository;

    @GetMapping
    public List<Edge> getEdges() {
        return edgeRepository.findAll();
    }

    @RequestMapping(value = "/bytwopoints", method = RequestMethod.GET)
    public List<Edge> getEdgesByTwoPoints(@RequestParam(value = "start", required = true) String s,
                                            @RequestParam(value = "destination", required = true) String d){
        return edgeRepository.findByStartPointIsAndDestinationPointIs(s, d);
    }

    @RequestMapping(value = "/bypoint", method = RequestMethod.GET)
    public List<Edge> getEdgesByPoint(@RequestParam(value = "start", required = false) String s,
                                        @RequestParam(value = "destination", required = false) String d){
        return edgeRepository.findByStartPointIsOrDestinationPointIs(s, d);
    }

    @RequestMapping(value = "/bydistance", method = RequestMethod.GET)
    public List<Edge> getEdgesByDistance(@RequestParam(value = "distance", required = true) Double d) {
        return edgeRepository.findByDistance(d);
    }

    @RequestMapping(value = "/byduration", method = RequestMethod.GET)
    public List<Edge> getEdgesByDuration(@RequestParam(value = "duration", required = true) Double d) {
        return edgeRepository.findByDuration(d);
    }

    @RequestMapping(value = "/bycost", method = RequestMethod.GET)
    public List<Edge> getEdgesByCost(@RequestParam(value = "cost", required = true) Double d) {
        return edgeRepository.findByCost(d);
    }

    @RequestMapping(value = "/bytransporttype", method = RequestMethod.GET)
    public List<Edge> getEdgesByTransportType(@RequestParam(value = "transporttype", required = true) String s) {
        return edgeRepository.findByTransportType(s);
    }

    @RequestMapping(value = "/byedgetype", method = RequestMethod.GET)
    public List<Edge> getEdgesByEdgeType(@RequestParam(value = "edgetype", required = true) Integer i) {
        return edgeRepository.findByEdgeType(i);
    }
}
