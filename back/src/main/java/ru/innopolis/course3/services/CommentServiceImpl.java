package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import ru.innopolis.course3.Comment;
import ru.innopolis.course3.CommentService;
import ru.innopolis.course3.models.comment.CommentEntity;
import ru.innopolis.course3.models.comment.CommentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling comment data
 *
 * @author Danil Popov
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment>
                                implements CommentService {

    private CommentRepository repository;

    @Autowired
    public void setRepository(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void addNew(Comment o) {
        repository.save(CommentEntity.getCommentEntity(o));
    }

    @Override
    protected void removeById(long id, long updateDate) {
        //updateDate
        repository.delete(id);
    }

    @Override
    protected void update(Comment o) {
        repository.save(CommentEntity.getCommentEntity(o));
    }

    @Override
    protected Comment getById(long id) {
        return CommentEntity.getComment(repository.findOne(id));
    }

    @Override
    protected List<Comment> getAll() {
        Iterable<CommentEntity> entities = repository.findAll();
        List<Comment> comments = new ArrayList<>();

        for (CommentEntity entity: entities) {
            comments.add(CommentEntity.getComment(entity));
        }
        return comments;
    }


    public List<Comment> getCommentsByArticleId(long id) {
        return template.execute(new TransactionCallback<List<Comment>>() {
            public List<Comment> doInTransaction(TransactionStatus txStatus) {
                List<Comment> comments = new ArrayList<>();
                try {
                    Iterable<CommentEntity> entities =
                            repository.findByArticleId(id);

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
