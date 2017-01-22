package ru.innopolis.course3.models.comment;

import ru.innopolis.course3.models.BaseEntity;
import ru.innopolis.course3.models.user.UserEntity;

import javax.persistence.*;

/**
 * @author Danil Popov
 */
@Entity(name = "comment")
public class CommentEntity implements BaseEntity {

    private Long id;
    private String source;
    private long date;
    private UserEntity user;
    private Long articleId;
    private long updateDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "date")
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Column(name = "article_id")
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Column(name = "update_date")
    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public static Comment getComment(CommentEntity entity) {
        Comment comment = new Comment();

        comment.setId(entity.getId());
        comment.setSource(entity.getSource());
        comment.setArticleId(entity.getArticleId());
        comment.setUser(UserEntity.getUser(entity.getUser()));
        comment.setDate(entity.getDate());
        comment.setUpdateDate(entity.getUpdateDate());

        return comment;
    }

    public static CommentEntity getCommentEntity(Comment o) {
        CommentEntity entity = new CommentEntity();
        entity.setId(o.getId());
        entity.setSource(o.getSource());
        entity.setArticleId(o.getArticleId());
        entity.setUser(UserEntity.getUserEntity(o.getUser()));
        entity.setDate(o.getDate());
        entity.setUpdateDate(o.getUpdateDate());
        return entity;
    }
}
