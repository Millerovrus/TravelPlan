package com.netcracker.travelplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.netcracker.travelplanner.entities.Edge;

@Repository
public interface EdgeRepository extends JpaRepository<Edge,Long> {


}
