package ru.innopolis.course3.models.article;

import ru.innopolis.course3.models.BaseEntity;
import ru.innopolis.course3.models.user.UserEntity;

import javax.persistence.*;

/**
 * @author Danil Popov
 */
@Entity(name = "article")
public class ArticleEntity implements BaseEntity {

    private int id;
    private String title;
    private String source;
    private UserEntity author;
    private long date;
    private long updateDate;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "article_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Column(name = "date")
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Column(name = "update_date")
    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public static Article getArticle(ArticleEntity entity) {
        Article article = new Article();
        article.setTitle(entity.getTitle());
        article.setSource(entity.getSource());
        article.setId(entity.getId());
        article.setAuthor(UserEntity.getUser(entity.getAuthor()));
        article.setDate(entity.getDate());
        article.setUpdateDate(entity.getUpdateDate());
        return article;
    }

    public static ArticleEntity getArticleEntity(Article article) {
        ArticleEntity entity = new ArticleEntity();
        entity.setId(article.getId());
        entity.setSource(article.getSource());
        entity.setTitle(article.getTitle());
        entity.setAuthor(UserEntity.getUserEntity(article.getAuthor()));
        entity.setDate(article.getDate());
        entity.setUpdateDate(article.getUpdateDate());
        return entity;
    }
}