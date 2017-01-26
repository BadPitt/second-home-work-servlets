package ru.innopolis.course3;

/**
 * @author Danil Popov
 */
public interface UserService extends BaseService<User> {
    User getUserByName(String name);
    void changeUsersPassword(String pass, User user);
}
