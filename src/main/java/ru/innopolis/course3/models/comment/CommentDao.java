package ru.innopolis.course3.models.comment;

import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.Dao;

import java.util.List;

/**
 * @author Danil Popov
 */
public interface CommentDao extends Dao<Comment> {

    List<Comment> getByArticleId(int id) throws DBException;

}
