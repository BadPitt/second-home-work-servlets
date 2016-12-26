package ru.innopolis.course3.models;

import java.util.List;

/**
 * Base interface for all DAO
 *
 * @author Danil Popov
 */
public interface Dao<T> {

    /**
     * Add new instance of entity to DB
     * @param o instance of entity
     */
    void add(T o);

    /**
     * Gets fields values from {@code T o} and then
     * update table's row
     *
     * @param o instance of entity
     */
    void update(T o);

    /**
     * Remove row from DB where ID equals
     * parameter id
     *
     * @param id instance's of entity id
     */
    void removeById(int id);

    /**
     * Gets all of entities instances
     *
     * @return {@code List<T>}
     */
    List<T> getAll();

    /**
     * Gets instance of entity with ID equals
     * parameter id
     *
     * @param id
     * @return {@code T} instance of entity
     */
    T getById(int id);
}
