package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.comment.Comment;
import ru.innopolis.course3.models.comment.CommentDao;
import ru.innopolis.course3.models.comment.CommentEntity;
import ru.innopolis.course3.models.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling comment data
 *
 * @author Danil Popov
 */
@Service
public class CommentService extends BaseService<Comment> {

    private CommentDao commentDao;

    @Autowired
    @Qualifier("commentDao")
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    protected void addNew(Comment o) {
        commentDao.add(CommentEntity.getCommentEntity(o));
    }

    @Override
    protected void removeById(int id, long updateDate) {
        commentDao.removeById(id, updateDate);
    }

    @Override
    protected void update(Comment o) {
        commentDao.update(CommentEntity.getCommentEntity(o));
    }

    @Override
    protected Comment getById(int id) {
        return CommentEntity.getComment(commentDao.getById(id));
    }

    @Override
    protected List<Comment> getAll() {
        List<CommentEntity> entities = commentDao.getAll();
        List<Comment> comments = new ArrayList<>();

        for (CommentEntity entity: entities) {
            comments.add(CommentEntity.getComment(entity));
        }
        return comments;
    }


    public List<Comment> getCommentsByArticleId(int id) throws DBException {
        return template.execute(new TransactionCallback<List<Comment>>() {
            public List<Comment> doInTransaction(TransactionStatus txStatus) {
                List<Comment> comments = new ArrayList<>();
                try {
                    List<CommentEntity> entities = commentDao.getByArticleId(id);

                    for (CommentEntity entity: entities) {
                        comments.add(CommentEntity.getComment(entity));
                    }
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return comments;
            }
        });
    }
}
