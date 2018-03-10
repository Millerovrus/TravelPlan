package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.entities.Edge;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
@Singleton
public class YandexExecutor implements ExecutorManager {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public List<Edge> execute(List<Callable<List<Edge>>> taskList) {

        List<Edge> edgeList = new ArrayList<>();

        List<Future<List<Edge>>> futures = Collections.synchronizedList(new ArrayList<>());
        /*try {
            futures = executorService.invokeAll(taskList, 1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        taskList.forEach(callable -> futures.add(executorService.submit(callable)));

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<List<Edge>> future : futures) {
            try {
                if(future.get() != null ){
                    edgeList.addAll(future.get());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return edgeList;
    }

}
