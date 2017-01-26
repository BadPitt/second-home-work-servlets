package ru.innopolis.course3;

import java.io.Serializable;

/**
 * POJO for comment entity
 *
 * @see BaseModel
 * @author Danil Popov
 */
public class Comment extends BaseModel implements Serializable {

    private long id;
    private String source;
    private long date;
    private User user;
    private long articleId;
    private long updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
