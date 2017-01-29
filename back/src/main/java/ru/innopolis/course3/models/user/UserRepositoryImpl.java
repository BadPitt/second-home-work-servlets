package ru.innopolis.course3.models.user;

import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danil Popov
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    private static final String CHANGE_PASSWORD = "UPDATE P_USER " +
            " SET PASSWORD=?" +
            " WHERE USER_ID=? AND VERSION=?;";
    private static final String GET_BY_NAME = "SELECT " +
            " u " +
            " FROM p_user u " +
            " WHERE u.name=?1";

    /*private static final String GET_BY_NAME = "SELECT " +
            " u.user_id, u.name, u.password, u.enabled, u.version, ur.role" +
            " FROM P_USER u " +
            " join user_to_roles utr on u.user_id = utr.user_id " +
            " JOIN user_roles ur on utr.role_id=ur.id" +
            " WHERE name=?";*/

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

    @Override
    @Transactional
    public UserDetailsImpl findUserDetailsByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> from = query.from(UserEntity.class);
        query.select(from);
        Predicate condition = builder.equal(from.get("name"), name);
        query.where(condition);
        UserEntity entity = entityManager.createQuery(query).getSingleResult();

        User user = UserEntity.getUser(entity);
        UserDetailsImpl ud = new UserDetailsImpl();
        ud.setName(user.getName());
        ud.setPassword(user.getPassword());
        List<GrantedAuthorityImpl> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            GrantedAuthorityImpl ga = new GrantedAuthorityImpl();
            ga.setName(role.getName());
            authorities.add(ga);
        }
        ud.setRoles(authorities);
        return ud;
    }

}
