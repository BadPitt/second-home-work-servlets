package ru.innopolis.course3.models.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.DBConnection;
import ru.innopolis.course3.models.Dao;
import ru.innopolis.course3.models.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for comment entity
 *
 * @author Danil Popov
 * @see Dao
 * @see Comment
 */
public class CommentDao implements Dao<Comment> {

    private static final Logger logger = LoggerFactory.getLogger(CommentDao.class);

    private static final String ADD_COMMENT = "INSERT INTO COMMENT " +
            " (SOURCE, DATE, ARTICLE_ID, USER_ID)" +
            " VALUES (?, ?, ?, ?);";
    private static final String UPDATE_COMMENT = "UPDATE COMMENT " +
            " SET SOURCE=?, DATE=?, ARTICLE_ID=?, USER_ID=?" +
            " WHERE COMMENT_ID=?;";
    private static final String DELETE_COMMENT = "DELETE FROM COMMENT " +
            " WHERE COMMENT_ID = ?;";
    private static final String GET_ALL_COMMENTS = "SELECT C.COMMENT_ID, C.SOURCE," +
            " C.DATE, C.ARTICLE_ID, C.USER_ID, U.NAME, U.IS_ACTIVE, U.is_admin" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID;";
    private static final String GET_COMMENT = "SELECT C.COMMENT_ID, C.SOURCE," +
            " C.DATE, C.ARTICLE_ID, C.USER_ID, U.NAME, U.IS_ACTIVE, U.is_admin" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID " +
            " WHERE C.COMMENT_ID = ?;";
    private static final String GET_ALL_COMMENTS_BY_ARTICLE = "SELECT C.COMMENT_ID, C.SOURCE," +
            " C.DATE, C.ARTICLE_ID, C.USER_ID, U.NAME, U.IS_ACTIVE, U.is_admin" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID" +
            " WHERE C.ARTICLE_ID=?;";

    /**
     * @see Dao#add(Object)
     * @param o Comment which will add
     */
    @Override
    public void add(Comment o) {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(ADD_COMMENT)) {

            statement.setString(1, o.getSource());
            statement.setLong(2, o.getDate());
            statement.setInt(3, o.getArticleId());
            statement.setInt(4, o.getUser().getId());
            statement.execute();

        } catch (SQLException e) {
            logger.error("add COMMENT sql exception", e);
        }
    }

    /**
     * @see Dao#update(Object)
     * @param o Comment which will update
     */
    @Override
    public void update(Comment o) {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_COMMENT)) {

            statement.setString(1, o.getSource());
            statement.setLong(2, o.getDate());
            statement.setInt(3, o.getArticleId());
            statement.setInt(4, o.getUser().getId());
            statement.setInt(5, o.getId());
            statement.execute();

        } catch (SQLException e) {
            logger.error("update COMMENT sql exception", e);
        }
    }

    /**
     * @see Dao#removeById(int)
     * @param id Comments's id for removing from DB Comment with this id
     */
    @Override
    public void removeById(int id) {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_COMMENT)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            logger.error("delete COMMENT sql exception", e);
        }
    }

    /**
     * @see Dao#getAll()
     * @return {@code List<Comment>} which contains all Comment from DB
     */
    @Override
    public List<Comment> getAll() {
        List<Comment> list = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_ALL_COMMENTS)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Comment comment = new Comment();
                comment.setId(result.getInt(1));
                comment.setSource(result.getString(2));
                comment.setDate(result.getLong(3));
                comment.setArticleId(result.getInt(4));
                User user = new User();
                user.setId(result.getInt(5));
                user.setName(result.getString(6));
                user.setIsActive(result.getBoolean(7));
                user.setIsAdmin(result.getBoolean(8));
                comment.setUser(user);
                list.add(comment);
            }

        } catch (SQLException e) {
            logger.error("get all COMMENT sql exception", e);
        }
        return list;
    }

    /**
     * @see Dao#getById(int)
     * @param id
     * @return Comment with current id
     */
    @Override
    public Comment getById(int id) {
        Comment comment = new Comment();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_COMMENT)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                comment.setId(result.getInt(1));
                comment.setSource(result.getString(2));
                comment.setDate(result.getLong(3));
                comment.setArticleId(result.getInt(4));
                User user = new User();
                user.setId(result.getInt(5));
                user.setName(result.getString(6));
                user.setIsActive(result.getBoolean(7));
                user.setIsAdmin(result.getBoolean(8));
                comment.setUser(user);
            }

        } catch (SQLException e) {
            logger.error("get ARTICLE sql exception", e);
        }
        return comment;
    }

    /**
     * Gets all comment for current article
     *
     * @param id article's id
     * @return {@code List<Comment>}
     */
    public List<Comment> getByArticleId(int id) {
        List<Comment> list = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_ALL_COMMENTS_BY_ARTICLE)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Comment comment = new Comment();
                comment.setId(result.getInt(1));
                comment.setSource(result.getString(2));
                comment.setDate(result.getLong(3));
                comment.setArticleId(result.getInt(4));
                User user = new User();
                user.setId(result.getInt(5));
                user.setName(result.getString(6));
                user.setIsActive(result.getBoolean(7));
                user.setIsAdmin(result.getBoolean(8));
                comment.setUser(user);
                list.add(comment);
            }

        } catch (SQLException e) {
            logger.error("get by article id COMMENT sql exception", e);
        }
        return list;
    }
}
