package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.models.article.ArticleDao;

import java.util.List;

/**
 * Service for handling article data
 *
 * @author Danil Popov
 */
@Service
public class ArticleService {

    private ArticleDao articleDao;

    @Autowired
    @Qualifier("articleDao")
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void addNewArticle(Article article) throws DBException {
        articleDao.add(article);
    }

    public void removeArticleById(int id, long updateDate) throws DBException {
        articleDao.removeById(id, updateDate);
    }

    public void updateArticle(Article article) throws DBException {
        articleDao.update(article);
    }

    public Article getArticleById(int id) throws DBException {
        return articleDao.getById(id);
    }

    public List<Article> getAllArticles() throws DBException {
        return articleDao.getAll();
    }
}
