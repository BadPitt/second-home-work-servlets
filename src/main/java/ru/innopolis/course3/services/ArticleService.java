package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.models.article.ArticleDao;
import ru.innopolis.course3.models.article.ArticleEntity;
import ru.innopolis.course3.models.article.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling article data
 *
 * @author Danil Popov
 */
@Service
public class ArticleService extends BaseService<Article> {

    private ArticleRepository repository;

    @Autowired
    public void setRepository(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void addNew(Article o) {
        repository.save(ArticleEntity.getArticleEntity(o));
    }

    @Override
    protected void removeById(long id, long updateDate) {
        // updateDate
        repository.delete(id);
    }

    @Override
    protected void update(Article o) {
        repository.save(ArticleEntity.getArticleEntity(o));
    }

    @Override
    protected Article getById(long id) throws DBException {
        return ArticleEntity.getArticle(repository.findOne(id));
    }

    @Override
    protected List<Article> getAll() throws DBException {
        Iterable<ArticleEntity> entities = repository.findAll();
        List<Article> articles = new ArrayList<>();

        for (ArticleEntity entity: entities) {
            articles.add(ArticleEntity.getArticle(entity));
        }
        return articles;
    }
}
