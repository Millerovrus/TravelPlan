package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.entities.Edge;

import java.util.List;
import java.util.concurrent.Callable;

@Deprecated
public interface ExecutorManager {

    List<Edge> execute(List<Callable<List<Edge>>> taskList);

}
