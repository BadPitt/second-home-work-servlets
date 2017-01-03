package ru.innopolis.course3.models.user;

import ru.innopolis.course3.models.DBException;

import java.util.List;

/**
 * Service for handling User data
 * @author Danil Popov
 */
public class UserService {

    private static UserDao userDao = new UserDao();

    public static void addNewUser(User user) throws DBException {
        userDao.add(user);
    }

    public static void removeUserById(int id, long version) throws DBException {
        userDao.removeById(id, version);
    }

    public static void updateUser(User user) throws DBException {
        userDao.update(user);
    }

    public static User getUserById(int id) throws DBException {
        return userDao.getById(id);
    }

    public static User getUserByName(String name) throws DBException {
        return userDao.getByName(name);
    }

    public static List<User> getAllUsers() throws DBException {
        return userDao.getAll();
    }

    public static void changeUsersPassword(String pass, User user) throws DBException {
        userDao.changePassword(pass, user);
    }
}
