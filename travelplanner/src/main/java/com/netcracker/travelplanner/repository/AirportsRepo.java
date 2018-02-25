package com.netcracker.travelplanner.repository;

import com.netcracker.travelplanner.entities.newKiwi.MyPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportsRepo extends JpaRepository<MyPoint,Long> {

    @Query("select a from MyPoint a where a.cityName = ?1 and a.name = ?1 and a.type='city'")
    MyPoint myFind(String name);

    @Query("select a from MyPoint a where a.code = :code and a.type= :typ ")
    MyPoint getByCodeAndType(@Param("code") String code, @Param("typ") String type);

}
