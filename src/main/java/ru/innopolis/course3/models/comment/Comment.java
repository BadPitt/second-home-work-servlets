package ru.innopolis.course3.models.comment;

import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.BaseModel;

/**
 * Created by danil on 24/12/16.
 */
public class Comment extends BaseModel {

    private int id;
    private String source;
    private long date;
    private User user;
    private int articleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
