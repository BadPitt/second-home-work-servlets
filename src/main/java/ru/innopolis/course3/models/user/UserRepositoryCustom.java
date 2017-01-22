package ru.innopolis.course3.models.user;

/**
 * @author Danil Popov
 */
public interface UserRepositoryCustom {
    void changePassword(String password, User user);
}
