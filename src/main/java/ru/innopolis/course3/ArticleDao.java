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
public class ArticleDao implements Dao<Article> {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDao.class);

    private static final String ADD_ARTICLE = "INSERT INTO ARTICLE " +
            "(TITLE, SOURCE, USER_ID) VALUES (?, ?, ?)";
    private static final String UPDATE_ARTICLE = "UPDATE ARTICLE " +
            " SET TITLE=?, SOURCE=?, USER_ID=? WHERE ARTICLE_ID=?;";
    private static final String DELETE_ARTICLE = "DELETE FROM ARTICLE " +
            "WHERE ARTICLE_ID = ?;";
    private static final String GET_ALL_ARTICLE = "SELECT ARTICLE_ID, " +
            "TITLE, SOURCE, USER_ID FROM ARTICLE;";

    @Override
    public void add(Article o) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(ADD_ARTICLE)) {

            statement.setString(1, o.getTitle());
            statement.setString(2, o.getSource());
            statement.setInt(3, o.getAuthor().getId());
            statement.execute();

        } catch (SQLException e) {
            logger.error("add ARTICLE sql exception", e);
        }
    }

    @Override
    public void update(Article o) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(UPDATE_ARTICLE)) {

            statement.setString(1, o.getTitle());
            statement.setString(2, o.getSource());
            statement.setInt(3, o.getAuthor().getId());
            statement.setInt(4, o.getId());
            statement.execute();

        } catch (SQLException e) {
            logger.error("update ARTICLE sql exception", e);
        }
    }

    @Override
    public void removeById(int id) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(DELETE_ARTICLE)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            logger.error("delete ARTICLE sql exception", e);
        }
    }

    @Override
    public List<Article> getAll() {
        List<Article> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = conn.prepareStatement(GET_ALL_ARTICLE)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Article article = new Article();
                article.setId(result.getInt(1));
                article.setTitle(result.getString(2));
                article.setSource(result.getString(3));
                //article.setDate(result.get);
                //article.setAuthor();
                list.add(article);
            }

        } catch (SQLException e) {
            logger.error("get all ARTICLE sql exception", e);
        }
        return list;
    }

    @Override
    public Article getById(int id) {
        return null;
    }
}
