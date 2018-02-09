package com.netcracker.travelplanner.service;

import com.netcracker.travelplanner.entities.Edge;

import java.util.List;

public interface IntegrationAPIService {

    /**
     * метод возвращает список ребер между двумя точками
     * @param from название города отправления
     * @param to название города назначения
     * @return вернет 3 ребра: дешевый, быстрый, оптимальный
     */
    List<Edge> getEdgesFromTo(String from, String to);

    /**
     * метод для получения списка городов вблизи заданного города
     * радиус по умолчанию 500 км
     * @param city
     * @return вернет список городов
     */
    List<String> getClosesCities(String city);

}
