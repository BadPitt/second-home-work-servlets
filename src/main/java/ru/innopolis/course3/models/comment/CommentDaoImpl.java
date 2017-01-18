package ru.innopolis.course3.models.comment;

import org.springframework.stereotype.Repository;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
            " U.NAME, U.enabled, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID;";
    private static final String GET_COMMENT_BY_ID = "SELECT " +
            " C.COMMENT_ID, C.SOURCE, C.DATE, C.ARTICLE_ID, C.USER_ID, " +
            " U.NAME, U.enabled, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID " +
            " WHERE C.COMMENT_ID = ?;";
    private static final String GET_ALL_COMMENTS_BY_ARTICLE = "SELECT " +
            " C.COMMENT_ID, C.SOURCE, C.DATE, C.ARTICLE_ID, C.USER_ID, " +
            " U.NAME, U.enabled, C.UPDATE_DATE" +
            " FROM COMMENT C join P_USER U on C.USER_ID=U.USER_ID" +
            " WHERE C.ARTICLE_ID=?;";

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @see Dao#add(ru.innopolis.course3.models.BaseEntity)
     * @param o Comment which will add
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void add(CommentEntity o) {
        entityManager.createNativeQuery(ADD_COMMENT)
                .setParameter(1, o.getSource())
                .setParameter(2, o.getDate())
                .setParameter(3, o.getArticleId())
                .setParameter(4, o.getUser().getId())
                .setParameter(5, o.getUpdateDate())
                .executeUpdate();
    }

    /**
     * @see Dao#update(ru.innopolis.course3.models.BaseEntity)
     * @param o Comment which will update
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void update(CommentEntity o) {
        entityManager.merge(o);
    }

    /**
     * @see Dao#removeById(int, long)
     * @param id Comments's id for removing from DB Comment with this id
     */
    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void removeById(int id, long updateDate) {
        entityManager.createNativeQuery(DELETE_COMMENT)
                .setParameter(1, id)
                .setParameter(2, updateDate)
                .executeUpdate();
    }

    /**
     * @see Dao#getAll()
     * @return {@code List<Comment>} which contains all Comment from DB
     */
    @Override
    //@Transactional(transactionManager = "transactionManager",
                    //readOnly = true)
    public List<CommentEntity> getAll() {
        List<CommentEntity> list = entityManager.createNativeQuery(GET_ALL_COMMENTS, CommentEntity.class)
                .getResultList();
        return list;
    }

    /**
     * @see Dao#getById(int)
     * @param id
     * @return Comment with current id
     */
    @Override
    //@Transactional(transactionManager = "transactionManager",
                    //readOnly = true)
    public CommentEntity getById(int id) {
        CommentEntity comment = (CommentEntity) entityManager
                .createNativeQuery(GET_COMMENT_BY_ID)
                .setParameter(1, id)
                .getSingleResult();
        return comment;
    }

    /**
     * Gets all comment for current article
     *
     * @param id article's id
     * @return {@code List<Comment>}
     */
    //@Transactional(transactionManager = "transactionManager",
            //readOnly = true)
    public List<CommentEntity> getByArticleId(int id) throws DBException {
        List<CommentEntity> list = entityManager
                .createNativeQuery(GET_ALL_COMMENTS_BY_ARTICLE, CommentEntity.class)
                .setParameter(1, id)
                .getResultList();
        return list;
    }
}
