package ru.innopolis.course3.models.user;

import java.util.List;

/**
 * Service for handling User data
 * @author Danil Popov
 */
public class UserService {

    private static UserDao userDao = new UserDao();

    public static void addNewUser(User user) {
        userDao.add(user);
    }

    public static void removeUserById(int id) {
        userDao.removeById(id);
    }

    public static void updateUser(User user) {
        userDao.update(user);
    }

    public static User getUserById(int id) {
        return userDao.getById(id);
    }

    public static User getUserByName(String name) {
        return userDao.getByName(name);
    }

    public static List<User> getAllUsers() {
        return userDao.getAll();
    }
}
