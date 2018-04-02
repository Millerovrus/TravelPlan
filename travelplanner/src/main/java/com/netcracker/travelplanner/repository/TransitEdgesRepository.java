package com.netcracker.travelplanner.repository;

import com.netcracker.travelplanner.entities.TransitEdge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitEdgesRepository extends JpaRepository<TransitEdge, Long> {
}
