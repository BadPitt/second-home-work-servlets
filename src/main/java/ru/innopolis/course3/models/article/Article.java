package ru.innopolis.course3.models.article;

import ru.innopolis.course3.models.BaseModel;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.utils.Utils;

/**
 * POJO for article entity
 *
 * @see BaseModel
 * @author Danil Popov
 */
public class Article extends BaseModel {

    private long id;
    private String title;
    private String source;
    private User author;
    private long date;
    private long updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    /**
     *
     * @return {@code String} which represent
     *          formatted article's date
     */
    public String getFormattedDate() {
        return Utils.getFormattedDate(date);
    }
}
