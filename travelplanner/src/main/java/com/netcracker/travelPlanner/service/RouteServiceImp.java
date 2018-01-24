package com.netcracker.travelPlanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.netcracker.travelPlanner.dao.RouteDao;
import com.netcracker.travelPlanner.entities.Route;

/**
 * Сервис, позволяющий работать через класс DAO(DataAccessObject), который добавляет
 * сущность в БД
 * Service помечаем класс как Bean, нужен для спинга
 */
@Deprecated
@Service
public class RouteServiceImp implements RouteService{

    /**
     * Autowired  Автоматическая инъекция значения Spring'ом (Spring сам найдёт нужный Bean)
     */
    @Autowired
    private RouteDao routeDao;

    /**
     * @param route Добавление маршрута, через метод класса DAO(DataAccessObject)
     * Transactional  Откат всех записей к предыдущему значению, если любая операция метода завершится неудачно.
     */
    @Transactional
    @Override
    public void add(Route route) {
        routeDao.add(route);
    }
}
