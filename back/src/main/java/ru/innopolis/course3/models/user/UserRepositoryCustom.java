package ru.innopolis.course3.models.user;

import ru.innopolis.course3.User;

/**
 * @author Danil Popov
 */
public interface UserRepositoryCustom {
    void changePassword(String password, User user);
}
