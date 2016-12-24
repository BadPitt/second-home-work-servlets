package ru.innopolis.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.innopolis.course3.Main.DATABASE_URL;

/**
 * @author Danil Popov
 */
public class UserDao implements Dao<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private static final String ADD_USER = "INSERT INTO P_USER (NAME, PASSWORD, ROLE, " +
            "IS_ACTIVE) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE P_USER SET NAME=?, " +
            "IS_ACTIVE=?, " +
            "ROLE=?;";
    private static final String CHANGE_PASSWORD = "UPDATE P_USER SET PASSWORD=?;";
    private static final String DELETE_USER = "DELETE FROM P_USER WHERE USER_ID=?;";
    private static final String GET_ALL_USERS = "SELECT USER_ID, NAME, IS_ACTIVE, " +
            "ROLE FROM P_USER";
    private static final String GET_USER_BY_ID = "SELECT USER_ID, NAME, IS_ACTIVE, ROLE " +
            "FROM P_USER WHERE USER_ID=?;";

    @Override
    public void add(User o) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(ADD_USER)) {

            statement.setString(1, o.getName());
            statement.setString(2, o.getPassword());
            statement.setBoolean(3, o.isActive());
            statement.setBoolean(4, o.isAdmin());
            statement.execute();

        } catch (SQLException e) {
            logger.error("add P_USER sql exception", e);
        }
    }

    @Override
    public void update(User o) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(UPDATE_USER)) {

            statement.setString(1, o.getName());
            statement.setBoolean(2, o.isAdmin());
            statement.setBoolean(3, o.isActive());
            statement.execute();

        } catch (SQLException e) {
            logger.error("update P_USER sql exception", e);
        }
    }

    @Override
    public void removeById(int id) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(DELETE_USER)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            logger.error("delete P_USER sql exception", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(GET_ALL_USERS)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getInt(1));
                user.setName(result.getString(2));
                user.setIsActive(result.getBoolean(3));
                user.setIsAdmin(result.getBoolean(4));
                list.add(user);
            }

        } catch (SQLException e) {
            logger.error("get all P_USER sql exception", e);
        }
        return list;
    }

    @Override
    public User getById(int id) {
        User user = new User();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(GET_USER_BY_ID)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user.setId(result.getInt(1));
                user.setName(result.getString(2));
                user.setIsActive(result.getBoolean(3));
                user.setIsAdmin(result.getBoolean(4));
            }

        } catch (SQLException e) {
            logger.error("get all P_USER sql exception", e);
        }
        return user;
    }
}
