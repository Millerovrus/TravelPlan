package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import java.util.List;

public class YandexApi implements ApiInterface{

    public YandexApi(){
        InitializatorApi initializator = InitializatorApi.getInstance();
    }

    public List<Edge> findEdgesFromTo() {
        return null;
    }

    public List<Edge> findEdgesOneToAll(){
        return null;
    }

    public List<Edge> findEdgesAllToOne() {
        return null;
    }

    public List<Edge> findEdgesAllToAll() {
        return null;
    }
}
