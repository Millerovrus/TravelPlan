package ru.netcracker.travelPlanner.dao;

import org.springframework.stereotype.Repository;
import ru.netcracker.travelPlanner.entities.Edge;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Добавляем новую сущность в БД через объект EntityManager.
 * EntityManager создала фабрика EntutyManagerFactory, прописанная в AppConfig
 * Repository помечаем класс как Bean, нужен для спринга
 */
@Repository
public class EdgeDaoImp implements EdgeDao {

    /**
     * PersistenceContext  Автоматическое связывание менеджера сущностей с бином
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Добавление информации о ребре в БД
     * @param edge сущность, которую необходимо добавить в БД
     */
    @Override
    public void add(Edge edge) {
        entityManager.persist(edge);
    }
}
