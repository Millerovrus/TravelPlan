package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.entities.Edge;
import org.springframework.stereotype.Service;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Singleton
@Service
public class KiwiExecutor implements ExecutorManager {

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public List<Edge> execute(List<Callable<List<Edge>>> taskList) {

        List<Edge> edgeList = new ArrayList<>();

        List<Future<List<Edge>>> futures = null;
        try {
            futures = executorService.invokeAll(taskList, 2, TimeUnit.MINUTES);
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
