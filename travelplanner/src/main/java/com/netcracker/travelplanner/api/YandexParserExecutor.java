package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Singleton
public class YandexParserExecutor implements ExecutorManager{

    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    public List<Future<List<Edge>>> execute(List<Callable<List<Edge>>> taskList) {
        List<Future<List<Edge>>> futures = Collections.synchronizedList(new ArrayList<>());

        taskList.forEach(callable -> futures.add(executorService.submit(callable)));

        executorService.shutdown();

        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return futures;
    }
}
