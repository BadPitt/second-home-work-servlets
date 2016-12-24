package ru.innopolis.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.innopolis.course3.Main.DATABASE_URL;

/**
 * Created by danil on 24/12/16.
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
            " C.DATE, C.ARTICLE_ID, C.USER_ID, U.NAME, U.IS_ACTIVE, U.ROLE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID;";
    private static final String GET_COMMENT = "SELECT C.COMMENT_ID, C.SOURCE," +
            " C.DATE, C.ARTICLE_ID, C.USER_ID, U.NAME, U.IS_ACTIVE, U.ROLE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID " +
            " WHERE C.COMMENT_ID = ?;";

    @Override
    public void add(Comment o) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(ADD_COMMENT)) {

            statement.setString(1, o.getSource());
            statement.setLong(2, o.getDate());
            statement.setInt(4, o.getArticleId());
            statement.setInt(3, o.getUser().getId());
            statement.execute();

        } catch (SQLException e) {
            logger.error("add COMMENT sql exception", e);
        }
    }

    @Override
    public void update(Comment o) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(UPDATE_COMMENT)) {

            statement.setString(1, o.getSource());
            statement.setLong(2, o.getDate());
            statement.setInt(3, o.getArticleId());
            statement.setInt(4, o.getUser().getId());
            statement.setInt(5, o.getCommentId());
            statement.execute();

        } catch (SQLException e) {
            logger.error("update COMMENT sql exception", e);
        }
    }

    @Override
    public void removeById(int id) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(DELETE_COMMENT)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            logger.error("delete COMMENT sql exception", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(GET_ALL_COMMENTS)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Comment comment = new Comment();
                comment.setCommentId(result.getInt(1));
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

    @Override
    public Comment getById(int id) {
        Comment comment = new Comment();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(GET_COMMENT)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                comment.setCommentId(result.getInt(1));
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
}
