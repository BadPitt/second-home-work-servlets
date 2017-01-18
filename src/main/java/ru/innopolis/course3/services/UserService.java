package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserDao;
import ru.innopolis.course3.models.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling User data
 *
 * @author Danil Popov
 */
@Service
public class UserService extends BaseService<User> {

    private UserDao userDao;

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /*public void addNewUser(User user) {
        template.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {

                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return null;
            }
        });
    }*/

    /*public void removeUserById(int id, long version) {
        template.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {
                    userDao.removeById(id, version);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return null;
            }
        });
    }*/

    /*public void updateUser(User user) {
        template.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {
                    userDao.update(user);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return null;
            }
        });
    }*/

    /*public User getUserById(int id) {
        return template.execute(new TransactionCallback<User>() {
            public User doInTransaction(TransactionStatus txStatus) {
                User user = null;
                try {
                    user = userDao.getById(id);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return user;
            }
        });
    }*/

    /*public List<User> getAllUsers() {
        return template.execute(new TransactionCallback<List<User>>() {
            public List<User> doInTransaction(TransactionStatus txStatus) {
                List<User> users = new ArrayList<>();
                try {
                    users = userDao.getAll();
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return users;
            }
        });
    }*/

    public User getUserByName(String name) {
        return template.execute(new TransactionCallback<User>() {
            public User doInTransaction(TransactionStatus txStatus) {
                User user = null;
                try {
                    user = UserEntity.getUser(userDao.getByName(name));
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return user;
            }
        });
    }

    public void changeUsersPassword(String pass, User user) {
        template.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {
                    userDao.changePassword(pass, user);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return null;
            }
        });
    }

    @Override
    protected void addNew(User o) {
        userDao.add(UserEntity.getUserEntity(o));
    }

    @Override
    protected void removeById(int id, long version) {
        userDao.removeById(id, version);
    }

    @Override
    protected void update(User o) {
        userDao.update(UserEntity.getUserEntity(o));
    }

    @Override
    protected User getById(int id) {
        return UserEntity.getUser(userDao.getById(id));
    }

    @Override
    protected List<User> getAll() {
        List<UserEntity> entities = userDao.getAll();
        List<User> users = new ArrayList<>();

        for (UserEntity entity: entities) {
            users.add(UserEntity.getUser(entity));
        }

        return users;
    }
}
