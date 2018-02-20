package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ConvertPointsToListEdges {
    private List<Edge> resultList;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    ExecutorService executorService;
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
            if (!list.isEmpty()) {
                resultList.addAll(list);
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
    public List<Edge> findAll(String from, String to, LocalDate localDate){
        resultList = new ArrayList<>();
        executorService = Executors.newCachedThreadPool();

        logger.debug("Получение ближайших городов с аэропортами в округе города " + from + " и города " + to);
        //получаем аэропорты вокруг from
        List<String> citiesFrom = integrationAPIService.getClosesCities(from);
        //получаем аэропорты вокруг to
        List<String> citiesTo = integrationAPIService.getClosesCities(to);
        //удаляем лишние значения
        citiesFrom.remove(to);
        citiesTo.remove(from);
        for (String cityFrom : citiesFrom) {
            for (String cityTo : citiesTo) {
                if (cityFrom.equals(cityTo)){
                    citiesTo.remove(cityTo);
                    break;
                }
            }
        }
        logger.debug("Аэропорты вокруг " + from + " " + citiesFrom.toString());
        logger.debug("Аэропорты вокруг " + to + " " + citiesTo.toString());

        Runnable runnable = new ApiRunnable(from, to, localDate);
        executorService.execute(runnable);

        for (String cityFrom : citiesFrom) {
            runnable = new ApiRunnable(from, cityFrom, localDate);
            executorService.execute(runnable);
            runnable = new ApiRunnable(cityFrom, to, localDate);
            executorService.execute(runnable);
            for (String cityTo : citiesTo) {
                runnable = new ApiRunnable(from, cityTo, localDate);
                executorService.execute(runnable);
                runnable = new ApiRunnable(cityFrom, cityTo, localDate);
                executorService.execute(runnable);
                runnable = new ApiRunnable(cityTo, to, localDate);
                executorService.execute(runnable);
            }
        }

        executorService.shutdown();
        try {
            boolean finished = executorService.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}

