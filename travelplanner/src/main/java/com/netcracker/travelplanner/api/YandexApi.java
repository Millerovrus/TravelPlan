package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import java.util.List;

public class YandexApi implements ApiInterface{

    InitializatorApi initializatorApi;

    public YandexApi(InitializatorApi initializatorApi){
       this.initializatorApi = initializatorApi;
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
