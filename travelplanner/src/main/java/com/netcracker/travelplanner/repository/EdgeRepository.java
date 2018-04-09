package com.netcracker.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.netcracker.travelplanner.models.entities.Edge;

import java.util.List;

@Repository
public interface EdgeRepository extends JpaRepository<Edge,Integer>, JpaSpecificationExecutor<Edge> {
//    List<Edge> findByStartPointIsAndDestinationPointIs(String s, String d);
//    List<Edge> findByStartPointIsOrDestinationPointIs(String s, String d);
    List<Edge> findByDuration(double d);
    List<Edge> findByCost(double d);
    List<Edge> findByTransportType(String type);
}
