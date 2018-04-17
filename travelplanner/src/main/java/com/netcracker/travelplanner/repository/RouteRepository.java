package com.netcracker.travelplanner.repository;

import com.netcracker.travelplanner.models.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findByStartPointIsAndDestinationPointIs(String s, String d);
    List<Route> findByStartPointIsOrDestinationPointIs(String s, String d);
    List<Route> findByUserId(int id);
    List<Route> findByStartPointIsAndDestinationPointIsAndCreationDateIs(String s, String d, Date date);

    @Override
    List<Route> findAll();
}
