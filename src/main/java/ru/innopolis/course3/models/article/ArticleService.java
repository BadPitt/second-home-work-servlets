package ru.innopolis.course3.models.article;

import java.util.List;

/**
 * Created by danil on 25/12/16.
 */
public class ArticleService {

    private static ArticleDao articleDao = new ArticleDao();

    public static void addNewArticle(Article article) {
        articleDao.add(article);
    }

    public static void removeArticleById(int id) {
        articleDao.removeById(id);
    }

    public static void updateArticle(Article article) {
        articleDao.update(article);
    }

    public static Article getArticleById(int id) {
        return articleDao.getById(id);
    }

    public static List<Article> getAllArticles() {
        return articleDao.getAll();
    }
}
