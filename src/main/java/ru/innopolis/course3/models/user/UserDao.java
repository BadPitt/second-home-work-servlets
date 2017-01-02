package ru.innopolis.course3.models.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.DBConnection;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;

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
public class UserDao implements Dao<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private static final String ADD_USER = "INSERT INTO P_USER " +
            " (NAME, PASSWORD, IS_ACTIVE, IS_ADMIN, VERSION) " +
            " VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE P_USER " +
            " SET NAME=?, " +
            " IS_ACTIVE=?, " +
            " IS_ADMIN=?, " +
            " VERSION=?" +
            " WHERE USER_ID=? AND VERSION=?;";
    private static final String CHANGE_PASSWORD = "UPDATE P_USER " +
            " SET PASSWORD=?" +
            " WHERE USER_ID=?;";
    private static final String DELETE_USER = "DELETE FROM P_USER " +
            "WHERE USER_ID=? AND VERSION=?;";
    private static final String GET_ALL_USERS = "SELECT " +
            " USER_ID, NAME, IS_ACTIVE, IS_ADMIN, PASSWORD, VERSION " +
            " FROM P_USER";
    private static final String GET_USER_BY_ID = "SELECT " +
            " USER_ID, NAME, IS_ACTIVE, IS_ADMIN, PASSWORD, VERSION " +
            " FROM P_USER WHERE USER_ID=?;";
    private static final String GET_USER_BY_NAME = "SELECT " +
            " USER_ID, NAME, IS_ACTIVE, IS_ADMIN, PASSWORD, VERSION " +
            " FROM P_USER WHERE NAME=?;";

    /**
     * @see Dao#add(Object)
     *
     * @param o User which will add
     */
    @Override
    public void add(User o) throws DBException {
        precondition(o);
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(ADD_USER)) {

            statement.setString(1, o.getName());
            statement.setString(2, o.getPassword());
            statement.setBoolean(3, o.isActive());
            statement.setBoolean(4, o.isAdmin());
            statement.setLong(5, o.getVersion());
            statement.execute();

        } catch (SQLException e) {
            logger.error("add P_USER sql exception", e);
            throw new DBException();
        }
    }

    /**
     * @see Dao#update(Object)
     *
     * @param o User which will update
     */
    @Override
    public void update(User o) throws DBException {
        precondition(o);
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_USER)) {

            statement.setString(1, o.getName());
            statement.setBoolean(2, o.isActive());
            statement.setBoolean(3, o.isAdmin());
            statement.setLong(4, o.getVersion() + 1);
            statement.setInt(5, o.getId());
            statement.setLong(6, o.getVersion());
            statement.execute();

        } catch (SQLException e) {
            logger.error("update P_USER sql exception", e);
            throw new DBException();
        }
    }

    /**
     * @see Dao#removeById(int, long)
     *
     * @param id User's id for removing from DB User with this id
     */
    @Override
    public void removeById(int id, long version) throws DBException {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_USER)) {

            statement.setInt(1, id);
            statement.setLong(2, version);
            statement.execute();

        } catch (SQLException e) {
            logger.error("delete P_USER sql exception", e);
            throw new DBException();
        }
    }

    /**
     * @see Dao#getAll()
     *
     * @return {@code List<User>} which contains all Users from DB
     */
    @Override
    public List<User> getAll() throws DBException {
        List<User> list = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_ALL_USERS)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getInt(1));
                user.setName(result.getString(2));
                user.setIsActive(result.getBoolean(3));
                user.setIsAdmin(result.getBoolean(4));
                user.setPassword(result.getString(5));
                user.setVersion(result.getLong(6));
                list.add(user);
            }

        } catch (SQLException e) {
            logger.error("get all P_USER sql exception", e);
            throw new DBException();
        }
        return list;
    }

    /**
     * @see Dao#getById(int)
     *
     * @param id
     * @return User with current id
     */
    @Override
    public User getById(int id) throws DBException {
        User user = new User();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_USER_BY_ID)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user.setId(result.getInt(1));
                user.setName(result.getString(2));
                user.setIsActive(result.getBoolean(3));
                user.setIsAdmin(result.getBoolean(4));
                user.setPassword(result.getString(5));
                user.setVersion(result.getLong(6));
            }

        } catch (SQLException e) {
            logger.error("get user by id P_USER sql exception", e);
            throw new DBException();
        }
        return user;
    }

    /**
     * Finds User by name
     *
     * @param name
     * @return {@code User}
     */
    public User getByName(String name) throws DBException {
        User user = new User();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_USER_BY_NAME)) {

            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user.setId(result.getInt(1));
                user.setName(result.getString(2));
                user.setIsActive(result.getBoolean(3));
                user.setIsAdmin(result.getBoolean(4));
                user.setPassword(result.getString(5));
                user.setVersion(result.getLong(6));
            }

        } catch (SQLException e) {
            logger.error("get user by name P_USER sql exception", e);
            throw new DBException();
        }
        return user;
    }

    private void precondition(User o) throws DBException {
        if (o == null) {
            logger.error("USER null object exception");
            throw new DBException();
        }
    }
}
