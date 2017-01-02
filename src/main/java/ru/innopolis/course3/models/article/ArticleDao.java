package ru.innopolis.course3.models.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.DBConnection;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;
import ru.innopolis.course3.models.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for article entity
 *
 * @author Danil Popov
 * @see Dao
 * @see Article
 */
public class ArticleDao implements Dao<Article> {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDao.class);

    private static final String ADD_ARTICLE = "INSERT INTO ARTICLE " +
            "(TITLE, SOURCE, USER_ID, DATE, UPDATE_DATE)" +
            " VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_ARTICLE = "UPDATE ARTICLE " +
            " SET TITLE=?, SOURCE=?, DATE=?, USER_ID=?, UPDATE_DATE=?" +
            " WHERE ARTICLE_ID=? AND UPDATE_DATE=?;";
    private static final String DELETE_ARTICLE = "DELETE FROM ARTICLE " +
            " WHERE ARTICLE_ID = ? AND UPDATE_DATE=?;";
    private static final String GET_ALL_ARTICLE = "SELECT " +
            " A.ARTICLE_ID, A.TITLE, A.SOURCE," +
            " A.DATE, A.USER_ID, U.NAME, U.IS_ACTIVE, U.IS_ADMIN, U.VERSION, A.UPDATE_DATE" +
            " FROM ARTICLE A join P_USER U on A.USER_ID=U.USER_ID;";
    private static final String GET_ARTICLE = "SELECT " +
            " A.ARTICLE_ID, A.TITLE, A.SOURCE," +
            " A.DATE, A.USER_ID, U.NAME, U.IS_ACTIVE, U.IS_ADMIN, U.VERSION, A.UPDATE_DATE" +
            " FROM ARTICLE A join P_USER U on A.USER_ID=U.USER_ID " +
            " WHERE A.ARTICLE_ID = ?;";

    /**
     * @see Dao#add(Object)
     * @param o Article which will add
     */
    @Override
    public void add(Article o) throws DBException {
        precondition(o);
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(ADD_ARTICLE)) {

            statement.setString(1, o.getTitle());
            statement.setString(2, o.getSource());
            statement.setInt(3, o.getAuthor().getId());
            statement.setLong(4, o.getDate());
            statement.setLong(5, o.getUpdateDate());
            statement.execute();

        } catch (SQLException e) {
            logger.error("add ARTICLE sql exception", e);
            throw new DBException();
        }
    }

    /**
     * @see Dao#update(Object)
     * @param o Article which will update
     */
    @Override
    public void update(Article o) throws DBException {
        precondition(o);
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_ARTICLE)) {
            long oldUpdateDate = o.getUpdateDate();
            o.setUpdateDate(System.currentTimeMillis());

            statement.setString(1, o.getTitle());
            statement.setString(2, o.getSource());
            statement.setLong(3, o.getDate());
            statement.setInt(4, o.getAuthor().getId());
            statement.setLong(5, o.getUpdateDate());
            statement.setInt(6, o.getId());
            statement.setLong(7, oldUpdateDate);
            statement.execute();

        } catch (SQLException e) {
            logger.error("update ARTICLE sql exception", e);
            throw new DBException();
        }
    }

    /**
     * @see Dao#removeById(int, long)
     * @param id Article's id for removing from DB User with this id
     */
    @Override
    public void removeById(int id, long updateDate) throws DBException {
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_ARTICLE)) {

            statement.setInt(1, id);
            statement.setLong(2, updateDate);
            statement.execute();

        } catch (SQLException e) {
            logger.error("delete ARTICLE sql exception", e);
            throw new DBException();
        }
    }

    /**
     * @see Dao#getAll()
     * @return {@code List<Article>} which contains all Articles from DB
     */
    @Override
    public List<Article> getAll() throws DBException {
        List<Article> list = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_ALL_ARTICLE)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Article article = new Article();
                article.setId(result.getInt(1));
                article.setTitle(result.getString(2));
                article.setSource(result.getString(3));
                article.setDate(result.getLong(4));
                User user = new User();
                user.setId(result.getInt(5));
                user.setName(result.getString(6));
                user.setIsActive(result.getBoolean(7));
                user.setIsAdmin(result.getBoolean(8));
                user.setVersion(result.getLong(9));
                article.setAuthor(user);
                article.setUpdateDate(result.getLong(10));
                list.add(article);
            }

        } catch (SQLException e) {
            logger.error("get all ARTICLE sql exception", e);
            throw new DBException();
        }
        return list;
    }

    /**
     * @see Dao#getById(int)
     * @param id
     * @return Article with current id
     */
    @Override
    public Article getById(int id) throws DBException {
        Article article = new Article();
        try (Connection conn = DBConnection.getDbConnection();
             PreparedStatement statement = conn.prepareStatement(GET_ARTICLE)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                article.setId(result.getInt(1));
                article.setTitle(result.getString(2));
                article.setSource(result.getString(3));
                article.setDate(result.getLong(4));
                User user = new User();
                user.setId(result.getInt(5));
                user.setName(result.getString(6));
                user.setIsActive(result.getBoolean(7));
                user.setIsAdmin(result.getBoolean(8));
                user.setVersion(result.getLong(9));
                article.setAuthor(user);
                article.setUpdateDate(result.getLong(10));
            }

        } catch (SQLException e) {
            logger.error("get ARTICLE sql exception", e);
            throw new DBException();
        }
        return article;
    }

    private void precondition(Article o) throws DBException {
        if (o == null) {
            logger.error("ARTICLE null object exception");
            throw new DBException();
        } else if (o.getDate() == 0 && o.getUpdateDate() == 0) {
            long date = System.currentTimeMillis();
            o.setDate(date);
            o.setUpdateDate(date);
        } else if (o.getDate() == 0 || o.getUpdateDate() == 0) {
            long date = o.getDate() | o.getUpdateDate();
            o.setDate(date);
            o.setUpdateDate(date);
        }
    }
}
