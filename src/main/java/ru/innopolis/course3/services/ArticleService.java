package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.models.article.ArticleDao;
import ru.innopolis.course3.models.article.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling article data
 *
 * @author Danil Popov
 */
@Service
public class ArticleService extends BaseService<Article> {

    private ArticleDao articleDao;

    @Autowired
    @Qualifier("articleDao")
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    protected void addNew(Article o) {
        articleDao.add(ArticleEntity.getArticleEntity(o));
    }

    @Override
    protected void removeById(int id, long updateDate) {
        articleDao.removeById(id, updateDate);
    }

    @Override
    protected void update(Article o) {
        articleDao.update(ArticleEntity.getArticleEntity(o));
    }

    @Override
    protected Article getById(int id) throws DBException {
        return ArticleEntity.getArticle(articleDao.getById(id));
    }

    @Override
    protected List<Article> getAll() throws DBException {
        List<ArticleEntity> entities = articleDao.getAll();
        List<Article> articles = new ArrayList<>();

        for (ArticleEntity entity: entities) {
            articles.add(ArticleEntity.getArticle(entity));
        }
        return articles;
    }
}
