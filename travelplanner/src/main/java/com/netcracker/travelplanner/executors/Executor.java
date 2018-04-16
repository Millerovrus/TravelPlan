package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.api.ApiInterface;
import com.netcracker.travelplanner.models.entities.Edge;
import com.netcracker.travelplanner.models.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class Executor implements ExecutorMan {
    private static final Logger logger = LoggerFactory.getLogger(Executor.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

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
        tasks.forEach(task ->
                callables.add(() ->
                        apiInterface.findEdgesFromTo(task.getFrom()
                                , task.getTo()
                                , task.getDate()
                                , task.getNumberOfAdults()
                                , task.getNumberOfChildren()
                                , task.getNumberOfInfants()))
        );

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

            } catch (InterruptedException e) {
                logger.error("ошибка выполнения");

            } catch (ExecutionException e) {
                logger.error("ошибка запроса");
            }
        });

        /*Для теста. запись в файлы полученных эджей*/
//        logger.debug("edges from " + this.toString());
//
//        try (FileWriter writer = new FileWriter(new File("test" + this.toString() + ".txt"))) {
//            edgeList.forEach(edge -> {
//                try {
//                    writer.write(edge.getStartPoint() + " "
//                            + edge.getDestinationPoint() + " "
//                            + edge.getTransportType() + " "
//                            + edge.getCost() + " "
//                            + edge.getDuration() +" "
//                            + edge.getStartDate().toString()+" "
//                            + edge.getEndDate().toString()
//                            +"\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
        return edgeList;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}

