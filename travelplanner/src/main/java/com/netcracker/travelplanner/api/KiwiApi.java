package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import java.util.List;

public class KiwiApi implements ApiInterface {
    private InitializatorApi initializatorApi;

    public KiwiApi(InitializatorApi initializatorApi){
        this.initializatorApi = initializatorApi;
    }

    public List<Edge> findEdgesFromTo(){
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
