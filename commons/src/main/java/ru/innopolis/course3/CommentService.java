package ru.innopolis.course3;

import java.util.List;

/**
 * @author Danil Popov
 */
public interface CommentService extends BaseService<Comment> {
    List<Comment> getCommentsByArticleId(long id);
}
