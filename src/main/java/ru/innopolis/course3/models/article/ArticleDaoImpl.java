package ru.innopolis.course3.models.article;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;
import ru.innopolis.course3.models.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
@Transactional
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
            " A.DATE, A.USER_ID, U.NAME, U.enabled, U.VERSION, A.UPDATE_DATE" +
            " FROM ARTICLE A join P_USER U on A.USER_ID=U.USER_ID;";
    private static final String GET_ARTICLE = "SELECT " +
            " A.ARTICLE_ID, A.TITLE, A.SOURCE," +
            " A.DATE, A.USER_ID, U.NAME, U.enabled, U.VERSION, A.UPDATE_DATE" +
            " FROM ARTICLE A join P_USER U on A.USER_ID=U.USER_ID " +
            " WHERE A.ARTICLE_ID = ?;";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @see Dao#add(ru.innopolis.course3.models.BaseEntity)
     * @param o Article which will add
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void add(ArticleEntity o) {
        entityManager.createNativeQuery(ADD_ARTICLE)
            .setParameter(1, o.getTitle())
            .setParameter(2, o.getSource())
            .setParameter(3, o.getAuthor().getId())
            .setParameter(4, o.getDate())
            .setParameter(5, o.getUpdateDate())
            .executeUpdate();
    }

    /**
     * @see Dao#update(ru.innopolis.course3.models.BaseEntity)
     * @param o Article which will update
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void update(ArticleEntity o) {
        entityManager.merge(o);
    }

    /**
     * @see Dao#removeById(int, long)
     * @param id Article's id for removing from DB User with this id
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void removeById(int id, long updateDate) throws DBException {
        entityManager.createNativeQuery(DELETE_ARTICLE)
                .setParameter(1, id)
                .setParameter(2, updateDate)
                .executeUpdate();
    }

    /**
     * @see Dao#getAll()
     * @return {@code List<ArticleEntity>} which contains all Articles from DB
     */
    @Override
    //@Transactional(transactionManager = "transactionManager",
                    //readOnly = true)
    public List<ArticleEntity> getAll() throws DBException {
        Query query = entityManager.createNativeQuery(GET_ALL_ARTICLE, ArticleEntity.class);
        List<ArticleEntity> list = query.getResultList();
        return list;
    }

    /**
     * @see Dao#getById(int)
     * @param id
     * @return Article with current id
     */
    @Override
    //@Transactional(transactionManager = "transactionManager",
            //readOnly = true)
    public ArticleEntity getById(int id) throws DBException {
        ArticleEntity article = (ArticleEntity) entityManager
                .createNativeQuery(GET_ARTICLE, ArticleEntity.class)
                .setParameter(1, id)
                .getSingleResult();
        return article;
    }
}
