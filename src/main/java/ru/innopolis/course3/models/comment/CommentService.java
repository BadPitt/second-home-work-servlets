package ru.innopolis.course3.models.comment;

import java.util.List;

/**
 * Service for handling comment data
 *
 * @author Danil Popov
 */
public class CommentService {

    private static CommentDao commentDao = new CommentDao();

    public static void addNewComment(Comment comment) {
        commentDao.add(comment);
    }

    public static void removeCommentById(int id) {
        commentDao.removeById(id);
    }

    public static void updateComment(Comment comment) {
        commentDao.update(comment);
    }

    public static Comment getCommentById(int id) {
        return commentDao.getById(id);
    }

    public static List<Comment> getAllComments() {
        return commentDao.getAll();
    }

    public static List<Comment> getCommentsByArticleId(int id) {
        return commentDao.getByArticleId(id);
    }
}
