package ru.innopolis.course3.models.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling comment data
 *
 * @author Danil Popov
 */
@Service
public class CommentService {

    private CommentDao commentDao;

    @Autowired
    @Qualifier("commentDao")
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public void addNewComment(Comment comment) throws DBException {
        commentDao.add(comment);
    }

    public void removeCommentById(int id, long updateDate) throws DBException {
        commentDao.removeById(id, updateDate);
    }

    public void updateComment(Comment comment) throws DBException {
        commentDao.update(comment);
    }

    public Comment getCommentById(int id) throws DBException {
        return commentDao.getById(id);
    }

    public List<Comment> getAllComments() throws DBException {
        return commentDao.getAll();
    }


    public List<Comment> getCommentsByArticleId(int id) throws DBException {
        /* FIXME: Aspect changes my dao to proxy objects! They aren't instances of CommentDaoImpl */
        //if (commentDao instanceof CommentDao) {
            return ((CommentDao) commentDao).getByArticleId(id);
        //} else {
            //return new ArrayList<>();
        //}
    }
}
