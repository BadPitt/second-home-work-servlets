package ru.innopolis.course3.models.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
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
@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao {

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

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @see Dao#add(ru.innopolis.course3.models.BaseModel)
     * @param o Article which will add
     */
    @Override
    public void add(Article o) throws DBException {
        jdbcTemplate.update(ADD_ARTICLE,
                o.getTitle(),
                o.getSource(),
                o.getAuthor().getId(),
                o.getDate(),
                o.getUpdateDate());
    }

    /**
     * @see Dao#update(ru.innopolis.course3.models.BaseModel)
     * @param o Article which will update
     */
    @Override
    public void update(Article o) throws DBException {
        long oldUpdateDate = o.getUpdateDate();
        o.setUpdateDate(System.currentTimeMillis());

        jdbcTemplate.update(UPDATE_ARTICLE,
                o.getTitle(),
                o.getSource(),
                o.getDate(),
                o.getAuthor().getId(),
                o.getUpdateDate(),
                o.getId(),
                oldUpdateDate);
    }

    /**
     * @see Dao#removeById(int, long)
     * @param id Article's id for removing from DB User with this id
     */
    @Override
    public void removeById(int id, long updateDate) throws DBException {
        jdbcTemplate.update(DELETE_ARTICLE,
                id,
                updateDate);
    }

    /**
     * @see Dao#getAll()
     * @return {@code List<Article>} which contains all Articles from DB
     */
    @Override
    public List<Article> getAll() throws DBException {
        return jdbcTemplate.queryForObject(GET_ALL_ARTICLE,
                new RowMapper<List<Article>>() {
                    @Override
                    public List<Article> mapRow(ResultSet result, int rowNum) throws SQLException {
                        List<Article> list = new ArrayList<>();
                        do {
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
                        } while (result.next());
                        return list;
                    }
                });
    }

    /**
     * @see Dao#getById(int)
     * @param id
     * @return Article with current id
     */
    @Override
    public Article getById(int id) throws DBException {
        return jdbcTemplate.queryForObject(GET_ARTICLE,
                new RowMapper<Article>() {
                    @Override
                    public Article mapRow(ResultSet result, int rowNum) throws SQLException {
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
                        return article;
                    }
                },
                id);
    }
}
