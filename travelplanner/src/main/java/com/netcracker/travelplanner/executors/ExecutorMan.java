package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.api.ApiInterface;
import com.netcracker.travelplanner.models.entities.Edge;
import com.netcracker.travelplanner.models.Task;

import java.io.IOException;
import java.util.List;

public interface ExecutorMan {

    List<Edge> execute(List<Task> task, ApiInterface apiInterface) throws IOException;
}
