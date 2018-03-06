package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ExecutorManager {

    List<Edge>  execute(List<Callable<List<Edge>>> taskList);

}
