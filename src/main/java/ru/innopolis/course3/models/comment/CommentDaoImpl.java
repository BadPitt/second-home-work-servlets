package ru.innopolis.course3.models.comment;

import org.springframework.stereotype.Repository;
import ru.innopolis.course3.DBConnection;
import ru.innopolis.course3.models.DBException;
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
@Repository("commentDao")
public class CommentDaoImpl implements CommentDao {

    private static final String ADD_COMMENT = "INSERT INTO COMMENT " +
            " (SOURCE, DATE, ARTICLE_ID, USER_ID, UPDATE_DATE)" +
            " VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_COMMENT = "UPDATE COMMENT " +
            " SET SOURCE=?, DATE=?, ARTICLE_ID=?, USER_ID=?, UPDATE_DATE=?" +
            " WHERE COMMENT_ID=? AND UPDATE_DATE=?;";
    private static final String DELETE_COMMENT = "DELETE FROM COMMENT " +
            " WHERE COMMENT_ID = ? AND UPDATE_DATE=?;";
    private static final String GET_ALL_COMMENTS = "SELECT " +
            " C.COMMENT_ID, C.SOURCE, C.DATE, C.ARTICLE_ID, C.USER_ID, " +
            " U.NAME, U.IS_ACTIVE, U.IS_ADMIN, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID;";
    private static final String GET_COMMENT_BY_ID = "SELECT " +
            " C.COMMENT_ID, C.SOURCE, C.DATE, C.ARTICLE_ID, C.USER_ID, " +
            " U.NAME, U.IS_ACTIVE, U.IS_ADMIN, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID " +
            " WHERE C.COMMENT_ID = ?;";
    private static final String GET_ALL_COMMENTS_BY_ARTICLE = "SELECT " +
            " C.COMMENT_ID, C.SOURCE, C.DATE, C.ARTICLE_ID, C.USER_ID, " +
            " U.NAME, U.IS_ACTIVE, U.IS_ADMIN, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID" +
            " WHERE C.ARTICLE_ID=?;";

    /**
     * @see Dao#add(ru.innopolis.course3.models.BaseModel)
     * @param o Comment which will add
     */
    @Override
    public void add(Comment o) throws DBException {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(ADD_COMMENT)) {

            statement.setString(1, o.getSource());
            statement.setLong(2, o.getDate());
            statement.setInt(3, o.getArticleId());
            statement.setInt(4, o.getUser().getId());
            statement.setLong(5, o.getUpdateDate());
            statement.execute();

        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * @see Dao#update(ru.innopolis.course3.models.BaseModel)
     * @param o Comment which will update
     */
    @Override
    public void update(Comment o) throws DBException {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_COMMENT)) {
            long oldUpdateDate = o.getUpdateDate();
            o.setUpdateDate(System.currentTimeMillis());

            statement.setString(1, o.getSource());
            statement.setLong(2, o.getDate());
            statement.setInt(3, o.getArticleId());
            statement.setInt(4, o.getUser().getId());
            statement.setLong(5, o.getUpdateDate());
            statement.setInt(6, o.getId());
            statement.setLong(7, oldUpdateDate);
            statement.execute();

        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * @see Dao#removeById(int, long)
     * @param id Comments's id for removing from DB Comment with this id
     */
    @Override
    public void removeById(int id, long updateDate) throws DBException {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_COMMENT)) {

            statement.setInt(1, id);
            statement.setLong(2, updateDate);
            statement.execute();

        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /**
     * @see Dao#getAll()
     * @return {@code List<Comment>} which contains all Comment from DB
     */
    @Override
    public List<Comment> getAll() throws DBException {
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
                comment.setUpdateDate(result.getLong(9));
                list.add(comment);
            }

        } catch (SQLException e) {
            throw new DBException(e);
        }
        return list;
    }

    /**
     * @see Dao#getById(int)
     * @param id
     * @return Comment with current id
     */
    @Override
    public Comment getById(int id) throws DBException {
        Comment comment = new Comment();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_COMMENT_BY_ID)) {

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
                comment.setUpdateDate(result.getLong(9));
            }

        } catch (SQLException e) {
            throw new DBException(e);
        }
        return comment;
    }

    /**
     * Gets all comment for current article
     *
     * @param id article's id
     * @return {@code List<Comment>}
     */
    public List<Comment> getByArticleId(int id) throws DBException {
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
                comment.setUpdateDate(result.getLong(9));
                list.add(comment);
            }

        } catch (SQLException e) {
            throw new DBException(e);
        }
        return list;
    }
}
