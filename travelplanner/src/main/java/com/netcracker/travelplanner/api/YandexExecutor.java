package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


public class YandexExecutor implements ExecutorManager{

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public List<Edge> execute(List<Callable<List<Edge>>> taskList) {

        List<Edge> edgeList = Collections.synchronizedList(new ArrayList<>());

        List<Future<List<Edge>>> futures = new ArrayList<>();

        taskList.forEach(callable -> futures.add(executorService.submit(callable)));

        executorService.shutdown();

        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (Future<List<Edge>> future : futures) {

            try {
                edgeList.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return edgeList;
    }

}
