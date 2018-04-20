package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.api.ApiInterface;
import com.netcracker.travelplanner.model.entities.IntegrationError;
import com.netcracker.travelplanner.model.exceptions.APIException;
import com.netcracker.travelplanner.model.entities.Edge;
import com.netcracker.travelplanner.model.Task;
import com.netcracker.travelplanner.services.ErrorRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
public class Executor implements ExecutorMan {

    private static final Logger logger = LoggerFactory.getLogger(Executor.class);

    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

    public void setErrorRepositoryService(ErrorRepositoryService errorRepositoryService) {
        this.errorRepositoryService = errorRepositoryService;
    }

    private ErrorRepositoryService errorRepositoryService;

    @Value("${executor.sleep-time-millis}")
    private Integer sleepTime;

    @Value("${executor.timeout-minutes}")
    private Integer timeout;

    @Override
    public List<Edge> execute(List<Task> tasks, ApiInterface apiInterface) {

        List<Future<List<Edge>>> futures = new ArrayList<>();

        List<Callable<List<Edge>>> callables = new ArrayList<>();

        List<Edge> edgeList = new ArrayList<>();

        /*Формируем задачи для  выполнения из АПИ. передаем в метод findEdgesFromTo параметры запроса из сущности Task*/
        List<IntegrationError> errors = new ArrayList<>();

        tasks.forEach(task ->
                callables.add(() ->
                {
                    List<Edge> tempList = new ArrayList<>();
                    try {
                        tempList =  apiInterface.findEdgesFromTo(task.getFrom()
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
                            errors.add(new IntegrationError(description.toString(), new Date(), apiInterface.getClass().getName()));
                    }
                    return tempList;
                }));

        /*Отправляем  задачи на выполнение*/
        try {
            futures = executorService.invokeAll(callables, 4, TimeUnit.MINUTES);
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
                errors.add(new IntegrationError(ex.getMessage(), new Date(), "Executor"));
            }
        });

        if ( !errors.isEmpty()) {
            errorRepositoryService.saveErrors(errors);
        }

        return edgeList;
    }

    public Executor() {
        logger.info("create new Executor  :  "+ this.toString());
    }
}

