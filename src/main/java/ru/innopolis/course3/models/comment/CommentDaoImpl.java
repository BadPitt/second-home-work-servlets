package ru.innopolis.course3.models.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
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
            " U.NAME, U.enabled, U.ROLE_ID, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID;";
    private static final String GET_COMMENT_BY_ID = "SELECT " +
            " C.COMMENT_ID, C.SOURCE, C.DATE, C.ARTICLE_ID, C.USER_ID, " +
            " U.NAME, U.enabled, U.ROLE_ID, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID " +
            " WHERE C.COMMENT_ID = ?;";
    private static final String GET_ALL_COMMENTS_BY_ARTICLE = "SELECT " +
            " C.COMMENT_ID, C.SOURCE, C.DATE, C.ARTICLE_ID, C.USER_ID, " +
            " U.NAME, U.enabled, U.ROLE_ID, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID" +
            " WHERE C.ARTICLE_ID=?;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @see Dao#add(ru.innopolis.course3.models.BaseModel)
     * @param o Comment which will add
     */
    @Override
    public void add(Comment o) throws DBException {
        jdbcTemplate.update(ADD_COMMENT,
                o.getSource(),
                o.getDate(),
                o.getArticleId(),
                o.getUser().getId(),
                o.getUpdateDate());
    }

    /**
     * @see Dao#update(ru.innopolis.course3.models.BaseModel)
     * @param o Comment which will update
     */
    @Override
    public void update(Comment o) throws DBException {
        long oldUpdateDate = o.getUpdateDate();
        o.setUpdateDate(System.currentTimeMillis());

        jdbcTemplate.update(UPDATE_COMMENT,
                o.getSource(),
                o.getDate(),
                o.getArticleId(),
                o.getUser().getId(),
                o.getUpdateDate(),
                o.getId(),
                oldUpdateDate);
    }

    /**
     * @see Dao#removeById(int, long)
     * @param id Comments's id for removing from DB Comment with this id
     */
    @Override
    public void removeById(int id, long updateDate) throws DBException {
        jdbcTemplate.update(DELETE_COMMENT,
                id,
                updateDate);
    }

    /**
     * @see Dao#getAll()
     * @return {@code List<Comment>} which contains all Comment from DB
     */
    @Override
    public List<Comment> getAll() throws DBException {
        List<Comment> list = new ArrayList<>();
        try {
            list = jdbcTemplate.queryForObject(GET_ALL_COMMENTS,
                    new RowMapper<List<Comment>>() {
                        @Override
                        public List<Comment> mapRow(ResultSet result, int rowNum) throws SQLException {
                            List<Comment> list = new ArrayList<>();
                            do {
                                Comment comment = new Comment();
                                comment.setId(result.getInt(1));
                                comment.setSource(result.getString(2));
                                comment.setDate(result.getLong(3));
                                comment.setArticleId(result.getInt(4));
                                User user = new User();
                                user.setId(result.getInt(5));
                                user.setName(result.getString(6));
                                user.setIsActive(result.getBoolean(7));
                                user.setRoleId(result.getInt(8));
                                comment.setUser(user);
                                comment.setUpdateDate(result.getLong(9));
                                list.add(comment);
                            } while (result.next());
                            return list;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {

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
        Comment comment = null;
        try {
            comment = jdbcTemplate.queryForObject(GET_COMMENT_BY_ID,
                    new RowMapper<Comment>() {
                        @Override
                        public Comment mapRow(ResultSet result, int rowNum) throws SQLException {
                            Comment comment = new Comment();
                            comment.setId(result.getInt(1));
                            comment.setSource(result.getString(2));
                            comment.setDate(result.getLong(3));
                            comment.setArticleId(result.getInt(4));
                            User user = new User();
                            user.setId(result.getInt(5));
                            user.setName(result.getString(6));
                            user.setIsActive(result.getBoolean(7));
                            user.setRoleId(result.getInt(8));
                            comment.setUser(user);
                            comment.setUpdateDate(result.getLong(9));
                            return comment;
                        }
                    },
                    id);
        } catch (EmptyResultDataAccessException e) {

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
        try {
            list = jdbcTemplate.queryForObject(GET_ALL_COMMENTS_BY_ARTICLE,
                    new RowMapper<List<Comment>>() {
                        @Override
                        public List<Comment> mapRow(ResultSet result, int rowNum) throws SQLException {
                            List<Comment> list = new ArrayList<>();
                            do {
                                Comment comment = new Comment();
                                comment.setId(result.getInt(1));
                                comment.setSource(result.getString(2));
                                comment.setDate(result.getLong(3));
                                comment.setArticleId(result.getInt(4));
                                User user = new User();
                                user.setId(result.getInt(5));
                                user.setName(result.getString(6));
                                user.setIsActive(result.getBoolean(7));
                                user.setRoleId(result.getInt(8));
                                comment.setUser(user);
                                comment.setUpdateDate(result.getLong(9));
                                list.add(comment);
                            } while (result.next());
                            return list;
                        }
                    },
                    id);
        } catch (EmptyResultDataAccessException e) {

        }
        return list;
    }
}
