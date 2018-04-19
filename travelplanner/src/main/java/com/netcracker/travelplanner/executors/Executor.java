package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.api.ApiInterface;
import com.netcracker.travelplanner.model.exceptions.APIException;
import com.netcracker.travelplanner.model.entities.Edge;
import com.netcracker.travelplanner.model.Task;
import com.netcracker.travelplanner.services.ErrorSavingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class Executor implements ExecutorMan {
    private static final Logger logger = LoggerFactory.getLogger(Executor.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);
    private final ErrorSavingService errorSavingService;

    @Value("${executor.sleep-time-millis}")
    private Integer sleepTime;

    @Value("${executor.timeout-minutes}")
    private Integer timeout;

    @Autowired
    public Executor(ErrorSavingService errorSavingService) {
        this.errorSavingService = errorSavingService;
    }

    @Override
    public List<Edge> execute(List<Task> tasks, ApiInterface apiInterface) {

        List<Future<List<Edge>>> futures = new ArrayList<>();

        List<Callable<List<Edge>>> callables = new ArrayList<>();

        List<Edge> edgeList = new ArrayList<>();

        /*Формируем задачи для  выполнения из АПИ. передаем в метод findEdgesFromTo параметры запроса из сущности Task*/

        for (Task task :
                tasks) {
            List<Edge> edges = new ArrayList<>();
            try {
                edges = apiInterface.findEdgesFromTo(task.getFrom()
                        , task.getTo()
                        , task.getDate()
                        , task.getNumberOfAdults()
                        , task.getNumberOfChildren()
                        , task.getNumberOfInfants());
            } catch (APIException e) {
                StringBuilder description = new StringBuilder();
                description.append(e.getMessage());

                if (e.getCause() != null){
                    description.append(e.getCause().getMessage());
                }
                errorSavingService.saveError(description.toString(), apiInterface.getClass().getName());
            }
            if (edges != null && edges.size() > 0){
                List<Edge> finalEdges = edges;
                callables.add(() -> finalEdges);
            }
        }

        /*Отправляем  задачи на выполнение*/
        try {
            futures = executorService.invokeAll(callables, 200, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.error("Executor. ошибка запроса в InvokeAll");
        } catch (CancellationException e) {
            logger.error("Cancellation Exception");
        }

        /*Обрабатываем результат выполнения задач*/
        futures.forEach(listFuture -> {
            try {
                while ( ! listFuture.isDone()) {
                    logger.error("Thread sleep. Task is not complete");
                    Thread.sleep(sleepTime);
                }

                edgeList.addAll(listFuture.get());
            } catch (InterruptedException | ExecutionException ex){
                errorSavingService.saveError(ex.getMessage(), "Executor");
            }
        });
        return edgeList;
    }
}

