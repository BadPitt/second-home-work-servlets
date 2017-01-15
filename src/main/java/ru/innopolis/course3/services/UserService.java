package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserDao;

import java.util.List;

/**
 * Service for handling User data
 *
 * @author Danil Popov
 */
@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addNewUser(User user) throws DBException {
        userDao.add(user);
    }

    public void removeUserById(int id, long version) throws DBException {
        userDao.removeById(id, version);
    }

    public void updateUser(User user) throws DBException {
        userDao.update(user);
    }

    public User getUserById(int id) throws DBException {
        return userDao.getById(id);
    }

    public User getUserByName(String name) throws DBException {
        return userDao.getByName(name);
    }

    public List<User> getAllUsers() throws DBException {
        return userDao.getAll();
    }

    public void changeUsersPassword(String pass, User user) throws DBException {
        userDao.changePassword(pass, user);
    }
}
