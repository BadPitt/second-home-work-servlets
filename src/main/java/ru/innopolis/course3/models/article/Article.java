package ru.innopolis.course3.models.article;

import ru.innopolis.course3.models.BaseModel;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.utils.Utils;

/**
 * @author Danil Popov
 */
public class Article extends BaseModel {

    private int id;
    private String title;
    private String source;
    private User author;
    private long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getFormattedDate() {
        return Utils.getFormattedDate(date);
    }
}
