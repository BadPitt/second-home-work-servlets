package ru.innopolis.course3.models.user;

import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;

/**
 * @author Danil Popov
 */
public interface UserDao extends Dao<User> {

    User getByName(String name) throws DBException;

    void changePassword(String password, User user) throws DBException;
}
