package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import java.util.List;

public class ApiServiceManager implements ApiServiceInterface {

    private InitializatorApi intializator = InitializatorApi.getInstance();

    public ApiServiceManager(){

    }

    public List<Thread> getTaskList() {
        return null;
    }

    public List<Edge> foundEdges(){
        return null;
    }

    public List<Edge> runTasks(List<Thread> tasks){
        return null;
    }
}
