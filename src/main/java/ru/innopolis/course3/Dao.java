package ru.innopolis.course3;

import java.util.List;

/**
 * @author Danil Popov
 */
public interface Dao<T> {

    void add(T o);
    void update(T o);
    void removeById(int id);
    List<T> getAll();
    T getById(int id);

}
