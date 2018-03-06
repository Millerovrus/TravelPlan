package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
@Deprecated
@Service
public class ConvertPointsToListEdges {
    private List<Edge> resultList;
    private static final Logger logger = LoggerFactory.getLogger(ConvertPointsToListEdges.class);
    @Autowired
    private IntegrationAPIService integrationAPIService;

    class ApiRunnable implements Runnable{
        String from, to;
        LocalDate date;

        ApiRunnable(String from, String to, LocalDate date) {
            this.from = from;
            this.to = to;
            this.date = date;
        }

        @Override
        public void run() {
            List<Edge> list = integrationAPIService.getEdgesFromTo(from, to, date);
            synchronized (resultList) {
                if (!list.isEmpty()) {
                    resultList.addAll(list);
                    logger.debug("Получено ребро {} - {}", from, to);
                }
            }
        }
    }

    /**
     * Метод возвращает список всех рёбер, между всеми городами(аэропортами),
     * находязимися вблизи(по радиусу) города отправления и города назначения(включая эти города)
     * @param from название города отправления
     * @param to название города назначения
     * @param localDate дата
     * @return возврат списка рёбер
     */
    List<Edge> findAll(String from, String to, LocalDate localDate){
        resultList = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newCachedThreadPool();

        logger.debug("Получение ближайших городов с аэропортами в округе города {} и города {}", from, to);
        //получаем аэропорты вокруг from
        List<String> citiesFrom = integrationAPIService.getClosesCities(from);
        //получаем аэропорты вокруг to
        List<String> citiesTo = integrationAPIService.getClosesCities(to);
        //удаляем лишние аэропорты
        citiesFrom.remove(to);
        citiesTo.remove(from);
        //удаляем повторения аэропортов в списках
        for (String cityFrom : citiesFrom) {
            for (String cityTo : citiesTo) {
                if (cityFrom.equals(cityTo)) {
                    citiesTo.remove(cityTo);
                    break;
                }
            }
        }
        logger.debug("Аэропорты вокруг {}: {}", from, citiesFrom.toString());
        logger.debug("Аэропорты вокруг {}: {}", to, citiesTo.toString());

        //List<Runnable> threads = new ArrayList<>();

        Runnable runnable = new ApiRunnable(from, to, localDate);
        executorService.execute(runnable);

        for (String cityFrom : citiesFrom) {
            Runnable runnable1 = new ApiRunnable(from, cityFrom, localDate);
            executorService.execute(runnable1);
           // threads.add(runnable1);

            Runnable runnable2 = new ApiRunnable(cityFrom, to, localDate);
            executorService.execute(runnable2);
            //threads.add(runnable2);

            for (String cityTo : citiesTo) {
                Runnable runnable3 = new ApiRunnable(cityFrom, cityTo, localDate);
                executorService.execute(runnable3);
                //threads.add(runnable3);
            }
        }

        for (String cityTo : citiesTo) {
            Runnable runnable1 = new ApiRunnable(cityTo, to, localDate);
            executorService.execute(runnable1);
            //threads.add(runnable1);
            Runnable runnable2 = new ApiRunnable(from, cityTo, localDate);
            executorService.execute(runnable2);
            //threads.add(runnable2);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}