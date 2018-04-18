package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.models.entities.Route;
import com.netcracker.travelplanner.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RouteRepositoryService {
    @Autowired
    private RouteRepository routeRepository;

    /**
     * add route in database
     * @param route
     */
    public void save(Route route) {
        routeRepository.save(route);
    }

    /**
     * add list of routes in database
     * @param routeList
     */
    public void save(List<Route> routeList) {
        routeRepository.save(routeList);
    }

    /**
     * find all routes
     * @return list of routes
     */
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of routes by start AND destination point
     */
    public List<Route> findByStartPointAndDestinationPoint(String s, String d){
        return routeRepository.findByStartPointIsAndDestinationPointIs(s, d);
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @return list of routes by start OR destination point
     */
    public List<Route> findByStartPointOrDestinationPoint(String s, String d){
        return routeRepository.findByStartPointIsOrDestinationPointIs(s, d);
    }

    /**
     * @param id - user id
     * @return list of routes by user_id
     */
    public  List<Route> findByUserId(int id){
        return routeRepository.findByUserId(id);
    }

    /**
     * @param id
     * @return route by id
     */
    public Route findById(int id){
        return routeRepository.findOne(id);
    }

    /**
     * @param s - start point
     * @param d - destination point
     * @param date
     * @return list of routes by start AND destination point AND date
     */
    public List<Route> findByStartPointAndDestinationPointAndCreationDate(String s, String d, Date date){
        return routeRepository.findByStartPointIsAndDestinationPointIsAndCreationDateIs(s, d, date);
    }

    /**
     * Delete route by ID
     * @param id
     */
    public void deleteById(int id) {
        routeRepository.delete(id);
    }

    /**
     * delete all models managed by repository
     */
    public void deleteAll() {
        routeRepository.deleteAll();
    }
}
