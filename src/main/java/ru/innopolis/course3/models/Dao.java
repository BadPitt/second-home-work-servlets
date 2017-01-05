package ru.innopolis.course3.models;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Base interface for all DAO
 *
 * @author Danil Popov
 */
public interface Dao<T extends BaseModel> {

    /**
     * Add new instance of entity to DB
     * @param o instance of entity
     */
    void add(T o) throws DBException;

    /**
     * Gets fields values from {@code T o} and then
     * update table's row
     *
     * @param o instance of entity
     */
    void update(T o) throws DBException;

    /**
     * Remove row from DB where ID equals
     * parameter id
     *
     * @param id instance's of entity id
     */
    void removeById(int id, long version) throws DBException;

    /**
     * Gets all of entities instances
     *
     * @return {@code List<T>}
     */
    List<T> getAll() throws DBException;

    /**
     * Gets instance of entity with ID equals
     * parameter id
     *
     * @param id
     * @return {@code T} instance of entity
     */
    T getById(int id) throws DBException;
}
