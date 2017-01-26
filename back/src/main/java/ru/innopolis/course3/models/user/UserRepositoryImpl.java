package ru.innopolis.course3.models.user;

import ru.innopolis.course3.User;
import ru.innopolis.course3.Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Danil Popov
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    private static final String CHANGE_PASSWORD = "UPDATE P_USER " +
            " SET PASSWORD=?" +
            " WHERE USER_ID=? AND VERSION=?;";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void changePassword(String password, User user) {
        String hash = Utils.getPasswordHash(password);

        entityManager.createNativeQuery(CHANGE_PASSWORD)
                .setParameter(1, hash)
                .setParameter(2, user.getId())
                .setParameter(3, user.getVersion())
                .executeUpdate();
    }
}
