package com.netcracker.travelplanner.executors;

import com.netcracker.travelplanner.api.ApiInterface;
import com.netcracker.travelplanner.entities.Edge;
import com.netcracker.travelplanner.entities.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class Executor implements ExecutorMan {
    private static final Logger logger = LoggerFactory.getLogger(Executor.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

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
                                , task.getNumberOfChildren()))
        );

        /*Отправляем  задачи на выполнение*/
        try {
            futures = executorService.invokeAll(callables,4,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.error("Executor. ошибка запроса в InvokeAll");
        } catch (CancellationException e) {
            logger.error("Cancellation Exception");
        }

        /*Обрабатываем результат выполнения задач*/
        futures.forEach(listFuture -> {
            try {

                while ( ! listFuture.isDone()) {
                    System.out.println("Thread sleep. Task is not complete");
                    Thread.sleep(200);
                }

                edgeList.addAll(listFuture.get());

            } catch (InterruptedException e) {
                System.out.println("ошибка запроса");

            } catch (ExecutionException e) {
                System.out.println("ошибка выполнения");

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


}

