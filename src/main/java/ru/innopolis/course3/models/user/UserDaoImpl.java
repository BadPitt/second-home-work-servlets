package ru.innopolis.course3.models.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;
import ru.innopolis.course3.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for user entity
 *
 * @author Danil Popov
 * @see Dao
 * @see User
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private static final String ADD_USER = "INSERT INTO P_USER " +
            " (NAME, PASSWORD, SALT, IS_ACTIVE, IS_ADMIN, VERSION) " +
            " VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE P_USER " +
            " SET NAME=?, " +
            " IS_ACTIVE=?, " +
            " IS_ADMIN=?, " +
            " VERSION=?" +
            " WHERE USER_ID=? AND VERSION=?;";
    private static final String CHANGE_PASSWORD = "UPDATE P_USER " +
            " SET PASSWORD=?, SALT=?, VERSION=?" +
            " WHERE USER_ID=? AND VERSION=?;";
    private static final String DELETE_USER = "DELETE FROM P_USER " +
            "WHERE USER_ID=? AND VERSION=?;";
    private static final String GET_ALL_USERS = "SELECT " +
            " USER_ID, NAME, IS_ACTIVE, IS_ADMIN, PASSWORD, SALT, VERSION " +
            " FROM P_USER";
    private static final String GET_USER_BY_ID = "SELECT " +
            " USER_ID, NAME, IS_ACTIVE, IS_ADMIN, PASSWORD, SALT, VERSION " +
            " FROM P_USER WHERE USER_ID=?;";
    private static final String GET_USER_BY_NAME = "SELECT " +
            " USER_ID, NAME, IS_ACTIVE, IS_ADMIN, PASSWORD, SALT, VERSION " +
            " FROM P_USER WHERE NAME=?;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @see Dao#add(ru.innopolis.course3.models.BaseModel)
     *
     * @param o User which will add
     */
    @Override
    public void add(User o) throws DBException {
        jdbcTemplate.update(ADD_USER,
                o.getName(),
                o.getPassword(),
                o.getSalt(),
                o.getIsActive(),
                o.getIsAdmin(),
                o.getVersion());
    }

    /**
     * @see Dao#update(ru.innopolis.course3.models.BaseModel)
     *
     * @param o User which will update
     */
    @Override
    public void update(User o) throws DBException {
        jdbcTemplate.update(UPDATE_USER,
                o.getName(),
                o.getIsActive(),
                o.getIsAdmin(),
                o.getVersion() + 1,
                o.getId(),
                o.getVersion());
        o.setVersion(o.getVersion() + 1);
    }

    /**
     * @see Dao#removeById(int, long)
     *
     * @param id User's id for removing from DB User with this id
     */
    @Override
    public void removeById(int id, long version) throws DBException {
        jdbcTemplate.update(DELETE_USER,
                id,
                version);
    }

    /**
     * @see Dao#getAll()
     *
     * @return {@code List<User>} which contains all Users from DB
     */
    @Override
    public List<User> getAll() throws DBException {
        return jdbcTemplate.queryForObject(GET_ALL_USERS,
                new RowMapper<List<User>>() {
                    @Override
                    public List<User> mapRow(ResultSet result, int rowNum) throws SQLException {
                        List<User> list = new ArrayList<>();
                        do {
                            User user = new User();
                            user.setId(result.getInt(1));
                            user.setName(result.getString(2));
                            user.setIsActive(result.getBoolean(3));
                            user.setIsAdmin(result.getBoolean(4));
                            user.setPassword(result.getString(5));
                            user.setSalt(result.getString(6));
                            user.setVersion(result.getLong(7));
                            list.add(user);
                        } while (result.next());
                        return list;
                    }
                });
    }

    /**
     * @see Dao#getById(int)
     *
     * @param id
     * @return User with current id
     */
    @Override
    public User getById(int id) throws DBException {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID,
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet result, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(result.getInt(1));
                        user.setName(result.getString(2));
                        user.setIsActive(result.getBoolean(3));
                        user.setIsAdmin(result.getBoolean(4));
                        user.setPassword(result.getString(5));
                        user.setSalt(result.getString(6));
                        user.setVersion(result.getLong(7));
                        return user;
                    }
                },
                id);
    }

    /**
     * Finds User by name
     *
     * @param name
     * @return {@code User}
     */
    public User getByName(String name) throws DBException {
        return jdbcTemplate.queryForObject(GET_USER_BY_NAME,
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet result, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(result.getInt(1));
                        user.setName(result.getString(2));
                        user.setIsActive(result.getBoolean(3));
                        user.setIsAdmin(result.getBoolean(4));
                        user.setPassword(result.getString(5));
                        user.setSalt(result.getString(6));
                        user.setVersion(result.getLong(7));
                        return user;
                    }
                },
                name);
    }

    /**
     * Changes user's password's hash and salt
     *
     * @param password      user's password
     * @param user          user
     * @throws DBException  exception throws when
     *                      something goes wrong
     */
    public void changePassword(String password, User user) throws DBException {
        String[] hashAndSaltArray = Utils.getHashAndSaltArray(password);

        jdbcTemplate.update(CHANGE_PASSWORD,
                hashAndSaltArray[0],
                hashAndSaltArray[1],
                user.getVersion() + 1,
                user.getId(),
                user.getVersion());

        user.setVersion(user.getVersion() + 1);
    }
}
