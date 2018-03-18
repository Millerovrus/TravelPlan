package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.api.YandexParser;
import com.netcracker.travelplanner.entities.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.security.x509.EDIPartyName;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Singleton
@Service
public class YandexParserExecutor implements ExecutorManager {

    private final Logger logger = LoggerFactory.getLogger(YandexParserExecutor.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public List<Edge> execute(List<Callable<List<Edge>>> taskList) {

        List<Edge> edgeList = new ArrayList<>();

        List<Future<List<Edge>>> futures = Collections.synchronizedList(new ArrayList<>());
        try {
            futures = executorService.invokeAll(taskList, 60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<List<Edge>> future : futures) {
            try {
                if(future.get() != null ){
                    edgeList.addAll(future.get());
                }

            } catch (InterruptedException e) {
                logger.error("Ошибка выполнения");
            } catch (ExecutionException e) {
                logger.error("Ошибка запроса");
            }
        }

        return edgeList;
    }
}