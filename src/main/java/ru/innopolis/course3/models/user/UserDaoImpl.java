package ru.innopolis.course3.models.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;
import ru.innopolis.course3.utils.Utils;

import javax.persistence.*;
import java.util.List;

/**
 * DAO for user entity
 *
 * @author Danil Popov
 * @see Dao
 * @see User
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

    private static final String ADD_USER = "INSERT INTO P_USER " +
            " (NAME, PASSWORD, enabled, VERSION) " +
            " VALUES (?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE P_USER " +
            " SET NAME=?, " +
            " enabled=?, " +
            " ROLE_ID=?, " +
            " VERSION=?" +
            " WHERE USER_ID=? AND VERSION=?;";
    private static final String CHANGE_PASSWORD = "UPDATE P_USER " +
            " SET PASSWORD=?" +
            " WHERE USER_ID=? AND VERSION=?;";
    private static final String DELETE_USER = "DELETE FROM P_USER " +
            " WHERE USER_ID=? AND VERSION=?;";
    private static final String GET_ALL_USERS = "SELECT " +
            " USER_ID, NAME, enabled, PASSWORD, VERSION " +
            " FROM P_USER;";
    private static final String GET_USER_BY_ID = "SELECT " +
            " USER_ID, NAME, enabled, PASSWORD, VERSION " +
            " FROM P_USER WHERE USER_ID=?;";
    private static final String GET_USER_BY_NAME = "SELECT " +
            " USER_ID, NAME, enabled, PASSWORD, VERSION " +
            " FROM P_USER WHERE NAME=?;";

   // @PersistenceContext(type = PersistenceContextType.EXTENDED)
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @see Dao#add(ru.innopolis.course3.models.BaseEntity)
     *
     * @param o User which will add
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void add(UserEntity o) {
        //entityManager.persist(o);
        entityManager.createNativeQuery(ADD_USER)
                .setParameter(1, o.getName())
                .setParameter(2, o.getPassword())
                .setParameter(3, o.getIsActive())
                .setParameter(4, o.getVersion())
                .executeUpdate();
    }

    /**
     * @see Dao#update(ru.innopolis.course3.models.BaseEntity)
     *
     * @param o User which will update
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void update(UserEntity o) {
        entityManager.merge(o);
        // TODO: fix increment version this;
        o.setVersion(o.getVersion() + 1);
    }

    /**
     * @see Dao#removeById(int, long)
     *
     * @param id User's id for removing from DB User with this id
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void removeById(int id, long version) {
        entityManager.createNativeQuery(DELETE_USER)
                .setParameter(1, id)
                .setParameter(2, version)
                .executeUpdate();
    }

    /**
     * @see Dao#getAll()
     *
     * @return {@code List<User>} which contains all Users from DB
     */
    @Override
    //@Transactional(transactionManager = "transactionManager",
            //readOnly = true)
    public List<UserEntity> getAll() {
        Query query = entityManager.createNativeQuery(GET_ALL_USERS, UserEntity.class);
        List<UserEntity> list = query.getResultList();
        return list;
    }

    /**
     * @see Dao#getById(int)
     *
     * @param id
     * @return User with current id
     */
    @Override
    //@Transactional(transactionManager = "transactionManager",
            //readOnly = true)
    public UserEntity getById(int id) {
        UserEntity user = (UserEntity) entityManager
                .createNativeQuery(GET_USER_BY_ID, UserEntity.class)
                .setParameter(1, id)
                .getSingleResult();
        //entityManager.close();
        return user;
    }

    /**
     * Finds User by name
     *
     * @param name
     * @return {@code UserEntity}
     */

    //@Transactional(transactionManager = "transactionManager",
            //readOnly = true)
    public UserEntity getByName(String name) {
        Query query = entityManager.createNativeQuery(GET_USER_BY_NAME, UserEntity.class);
        UserEntity user = (UserEntity) query.setParameter(1, name)
                .getSingleResult();
        //entityManager.close();
        return user;
    }

    /**
     * Changes user's password's hash and salt
     *
     * @param password      user's password
     * @param user          user
     * @throws DBException  exception throws when
     *                      something goes wrong
     */

    //@Transactional(transactionManager = "transactionManager")
    public void changePassword(String password, User user) {
        String hash = Utils.getPasswordHash(password);

        entityManager.createNativeQuery(CHANGE_PASSWORD)
                .setParameter(1, hash)
                .setParameter(2, user.getId())
                .setParameter(3, user.getVersion())
                .executeUpdate();

        //entityManager.close();

        user.setVersion(user.getVersion() + 1);
    }
}
