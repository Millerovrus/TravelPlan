package com.netcracker.travelPlanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.netcracker.travelPlanner.entities.Edge;

@Repository
public interface EdgeRepository extends JpaRepository<Edge,Long> {


}
