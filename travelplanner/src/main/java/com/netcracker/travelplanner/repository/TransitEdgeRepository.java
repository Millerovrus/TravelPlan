package com.netcracker.travelplanner.repository;

import com.netcracker.travelplanner.model.entities.TransitEdge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitEdgeRepository extends JpaRepository<TransitEdge, Long> {
}
