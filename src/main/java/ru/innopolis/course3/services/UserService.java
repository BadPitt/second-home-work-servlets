package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserEntity;
import ru.innopolis.course3.models.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling User data
 *
 * @author Danil Popov
 */
@Service
public class UserService extends BaseService<User> {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserByName(String name) {
        return template.execute(new TransactionCallback<User>() {
            public User doInTransaction(TransactionStatus txStatus) {
                User user = null;
                try {
                    user = UserEntity.getUser(repository.findByName(name));
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
                    repository.changePassword(pass, user);
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
        repository.save(UserEntity.getUserEntity(o));
    }

    @Override
    protected void removeById(long id, long version) {
        //TODO: add version condition
        repository.delete(id);
    }

    @Override
    protected void update(User o) {
        repository.save(UserEntity.getUserEntity(o));
    }

    @Override
    protected User getById(long id) {
        return UserEntity.getUser(repository.findOne(id));
    }

    @Override
    protected List<User> getAll() {
        Iterable<UserEntity> entities = repository.findAll();
        List<User> users = new ArrayList<>();

        for (UserEntity entity: entities) {
            users.add(UserEntity.getUser(entity));
        }

        return users;
    }
}
