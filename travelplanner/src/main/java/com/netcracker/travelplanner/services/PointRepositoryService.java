package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.model.entities.Point;
import com.netcracker.travelplanner.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointRepositoryService {
    private final PointRepository pointRepository;

    @Autowired
    public PointRepositoryService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void save(Point point){
        pointRepository.save(point);
    }

    public void saveAll(Iterable<Point> points){
        pointRepository.save(points);
    }

}
