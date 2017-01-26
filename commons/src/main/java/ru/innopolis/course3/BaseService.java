package ru.innopolis.course3;

import java.util.List;

/**
 * @author Danil Popov
 */
public interface BaseService<T> {
    void addNewModelTransactionally(T o);
    void removeModelTransactionally(long id, long version);
    T getByIdTransactionally(long id);
    void updateTransactionally(T o);
    List<T> getAllTransactionally();
}
