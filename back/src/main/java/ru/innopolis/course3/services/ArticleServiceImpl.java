package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.innopolis.course3.Article;
import ru.innopolis.course3.ArticleService;
import ru.innopolis.course3.models.article.ArticleEntity;
import ru.innopolis.course3.models.article.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling article data
 *
 * @author Danil Popov
 */
@Service/*
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)*/
public class ArticleServiceImpl extends BaseServiceImpl<Article>
                                implements ArticleService {

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
    protected Article getById(long id) {
        return ArticleEntity.getArticle(repository.findOne(id));
    }


    //@Cacheable(value="movieFindCache", key="#name")
    @Override
    @Cacheable(value = "movieFindCache")
    public List<Article> getAll() {
        try {
            Thread.sleep(4_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterable<ArticleEntity> entities = repository.findAll();
        List<Article> articles = new ArrayList<>();

        for (ArticleEntity entity: entities) {
            articles.add(ArticleEntity.getArticle(entity));
        }
        return articles;
    }
}
