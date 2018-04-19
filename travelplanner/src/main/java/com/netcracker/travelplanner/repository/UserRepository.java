package com.netcracker.travelplanner.repository;

import com.netcracker.travelplanner.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByLastNameIsOrFirstNameIs(String lastName, String firstName);
    User findByEmail(String email);
}
