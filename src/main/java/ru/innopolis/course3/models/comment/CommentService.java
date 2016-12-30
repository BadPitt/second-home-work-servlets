package ru.innopolis.course3.models.comment;

import ru.innopolis.course3.models.DBException;

import java.util.List;

/**
 * Service for handling comment data
 *
 * @author Danil Popov
 */
public class CommentService {

    private static CommentDao commentDao = new CommentDao();

    public static void addNewComment(Comment comment) throws DBException {
        commentDao.add(comment);
    }

    public static void removeCommentById(int id, long updateDate) throws DBException {
        commentDao.removeById(id, updateDate);
    }

    public static void updateComment(Comment comment) throws DBException {
        commentDao.update(comment);
    }

    public static Comment getCommentById(int id) throws DBException {
        return commentDao.getById(id);
    }

    public static List<Comment> getAllComments() throws DBException {
        return commentDao.getAll();
    }

    public static List<Comment> getCommentsByArticleId(int id) throws DBException {
        return commentDao.getByArticleId(id);
    }
}
