package ru.innopolis.course3.models.article;

import ru.innopolis.course3.models.DBException;

import java.util.List;

/**
 * Service for handling article data
 *
 * @author Danil Popov
 */
public class ArticleService {

    private static ArticleDao articleDao = new ArticleDao();

    public static void addNewArticle(Article article) throws DBException {
        articleDao.add(article);
    }

    public static void removeArticleById(int id, long updateDate) throws DBException {
        articleDao.removeById(id, updateDate);
    }

    public static void updateArticle(Article article) throws DBException {
        articleDao.update(article);
    }

    public static Article getArticleById(int id) throws DBException {
        return articleDao.getById(id);
    }

    public static List<Article> getAllArticles() throws DBException {
        return articleDao.getAll();
    }
}
