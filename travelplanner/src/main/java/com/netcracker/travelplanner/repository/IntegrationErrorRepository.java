package com.netcracker.travelplanner.repository;

import com.netcracker.travelplanner.models.entities.IntegrationError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationErrorRepository extends JpaRepository<IntegrationError, Integer>{
}
