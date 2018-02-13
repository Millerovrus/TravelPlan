package com.netcracker.travelplanner.controllers;

import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.RouteType;
import com.netcracker.travelplanner.service.EdgeRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edges")
public class EdgeApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EdgeRepositoryService edgeRepositoryService;

    /**
     * @return list of all edges
     */
    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    public List<Edge> getEdges() {
        logger.info("Запрос на получение общего списка отрезков");
        return edgeRepositoryService.findAll();
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of edges by start AND destination point
     */
    @RequestMapping(value = "/findbytwopoints", method = RequestMethod.GET)
    public List<Edge> getEdgesByTwoPoints(@RequestParam(value = "start", required = true) String s,
                                            @RequestParam(value = "destination", required = true) String d){
        logger.info("Запрос на получение cписка отрезков с начальной точкой: " + s + " и конечной: " + d);
        return edgeRepositoryService.findByStartPointAndDestinationPoint(s, d);
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of edges by start OR destination point
     */
    @RequestMapping(value = "/findbypoint", method = RequestMethod.GET)
    public List<Edge> getEdgesByPoint(@RequestParam(value = "start", required = false) String s,
                                        @RequestParam(value = "destination", required = false) String d){
        logger.info("Запрос на получение cписка отрезков с начальной точкой: " + s + " или конечной: " + d);
        return edgeRepositoryService.findByStartPointOrDestinationPoint(s, d);
    }

    /**
     * @param d
     * @return list of edges by distance
     */
    @RequestMapping(value = "/findbydistance", method = RequestMethod.GET)
    public List<Edge> getEdgesByDistance(@RequestParam(value = "distance", required = true) double d) {
        logger.info("Запрос на получение cписка отрезков с distance = " + d);
        return edgeRepositoryService.findByDistance(d);
    }

    /**
     * @param d
     * @return list of edges by duration
     */
    @RequestMapping(value = "/findbyduration", method = RequestMethod.GET)
    public List<Edge> getEdgesByDuration(@RequestParam(value = "duration", required = true) double d) {
        logger.info("Запрос на получение cписка отрезков с duration = " + d);
        return edgeRepositoryService.findByDuration(d);
    }

    /**
     * @param d
     * @return list of edges by cost
     */
    @RequestMapping(value = "/findbycost", method = RequestMethod.GET)
    public List<Edge> getEdgesByCost(@RequestParam(value = "cost", required = true) double d) {
        logger.info("Запрос на получение cписка отрезков с cost = " + d);
        return edgeRepositoryService.findByCost(d);
    }

    /**
     * @param s
     * @return list of edges by transport type
     */
    @RequestMapping(value = "/findbytransporttype", method = RequestMethod.GET)
    public List<Edge> getEdgesByTransportType(@RequestParam(value = "transporttype", required = true) String s) {
        logger.info("Запрос на получение cписка отрезков с transporttype = " + s);
        return edgeRepositoryService.findByTransportType(s);
    }

    /**
     * @param type
     * @return list of edges by type
     */
    @RequestMapping(value = "/findbyedgetype", method = RequestMethod.GET)
    public List<Edge> getEdgesByEdgeType(@RequestParam(value = "edgetype", required = true) RouteType type) {
        logger.info("Запрос на получение cписка отрезков с edgetype = " + type);
        return edgeRepositoryService.findByEdgeType(type);
    }
}
