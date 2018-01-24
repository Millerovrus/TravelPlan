package com.netcracker.travelplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.netcracker.travelplanner.dao.EdgeDao;
import com.netcracker.travelplanner.entities.Edge;

/**
 * Сервис, позволяющий работать через класс DAO(DataAccessObject), который добавляет
 * сущность в БД
 * Service помечаем класс как Bean, нужен для спинга
 */
@Deprecated
@Service
public class EdgeServiceImp implements EdgeService{

    /**
     * Autowired  Автоматическая инъекция значения Spring'ом (Spring сам найдёт нужный Bean)
     */
    @Autowired
    private EdgeDao edgeDao;

    /**
     * @param edge Добавление ребра, через метод класса DAO(DataAccessObject)
     * Transactional  Откат всех записей к предыдущему значению, если любая операция метода завершится неудачно.
     */
    @Transactional
    @Override
    public void add(Edge edge) {
        edgeDao.add(edge);
    }
}
