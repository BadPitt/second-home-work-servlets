package ru.innopolis.course3;

import org.junit.*;
import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.models.article.ArticleDao;
import ru.innopolis.course3.models.comment.Comment;
import ru.innopolis.course3.models.comment.CommentDao;
import ru.innopolis.course3.models.user.User;
import ru.innopolis.course3.models.user.UserDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.innopolis.course3.DBConnection.*;

/**
 * Created by danil on 24/12/16.
 */
public class DaoTests {

    private static UserDao userDao = new UserDao();
    private static ArticleDao articleDao = new ArticleDao();
    private static CommentDao commentDao = new CommentDao();

    @BeforeClass
    public static void setConnectionTest() {
        DBConnection.isTest = true;
    }

    @AfterClass
    public static void setConnectionAfterTest() {
        DBConnection.isTest = false;
    }

    @Before
    public void init() {
        createUserTable();
        createArticleTable();
        createCommentTable();
    }

    @After
    public void drop() {
        dropCommentTable();
        dropArticleTable();
        dropUserTable();
    }

    @Test
    public void userAddGet() {
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        user.setIsActive(true);
        user.setIsAdmin(true);
        userDao.add(user);

        User userFromBd = userDao.getById(1);
        assertEquals(user, userFromBd);
    }

    @Test
    public void userRemove() {
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        user.setIsActive(true);
        user.setIsAdmin(true);
        userDao.add(user);

        userDao.removeById(1);

        User userFromBd = userDao.getById(1);
        assertEquals(0, userFromBd.getId());
    }

    @Test
    public void userUpdate() {
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        user.setIsActive(true);
        user.setIsAdmin(true);
        userDao.add(user);

        user.setName("Test");
        user.setIsActive(false);
        user.setIsAdmin(false);
        userDao.update(user);

        User updatedUser = userDao.getById(1);
        assertEquals(user, updatedUser);
    }

    @Test
    public void userGetAll() {
        List<User> users = new ArrayList<>();
        for (char c = 'a', b = 1; c < 'd'; c++, b++) {
            User user = new User();
            user.setId((int)b);
            user.setName("" + c);
            users.add(user);
            userDao.add(user);
        }

        List<User> usersFromBd = userDao.getAll();

        assertEquals(users, usersFromBd);
    }

    @Test
    public void articleAddGet() {
        User user = new User();
        user.setId(1);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setTitle("Test title");
        article.setSource("Loren ipsum dolore");
        article.setDate(System.currentTimeMillis());
        article.setAuthor(user);
        articleDao.add(article);

        Article articleFromBd = articleDao.getById(1);
        assertEquals(article, articleFromBd);
    }

    @Test
    public void articleRemove() {
        User user = new User();
        user.setId(1);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setTitle("Test title");
        article.setSource("Loren ipsum dolore");
        article.setDate(System.currentTimeMillis());
        article.setAuthor(user);
        articleDao.add(article);

        articleDao.removeById(1);

        Article articleFromBd = articleDao.getById(1);
        assertEquals(0, articleFromBd.getId());
    }

    @Test
    public void articleUpdate() {
        User user = new User();
        user.setId(1);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setTitle("Test title");
        article.setSource("Loren ipsum dolore");
        article.setDate(System.currentTimeMillis());
        article.setAuthor(user);
        articleDao.add(article);

        article.setTitle("updated title");
        article.setSource("updated source");
        article.setDate(System.currentTimeMillis() + 100L);
        articleDao.update(article);

        Article articleFromDb = articleDao.getById(1);
        assertEquals(article, articleFromDb);
    }

    @Test
    public void articleGetAll() {
        List<Article> articles = new ArrayList<>();
        for (char i = 'a', j = 1; i < 'e'; i++, j++) {
            User user = new User();
            user.setId(j);
            userDao.add(user);

            Article article = new Article();
            article.setId(j);
            article.setTitle("" + i);
            article.setSource("" + i);
            article.setDate(System.currentTimeMillis());
            article.setAuthor(user);
            articleDao.add(article);

            articles.add(article);
        }

        List<Article> articlesFromDb = articleDao.getAll();

        assertEquals(articles, articlesFromDb);
    }

    @Test
    public void commentAddGet() {
        User user = new User();
        user.setId(1);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setAuthor(user);
        articleDao.add(article);

        Comment comment = new Comment();
        comment.setId(1);
        comment.setSource("Test comment");
        comment.setDate(System.currentTimeMillis());
        comment.setArticleId(1);
        comment.setUser(user);
        commentDao.add(comment);

        Comment commentFromBd = commentDao.getById(1);
        assertEquals(comment, commentFromBd);
    }

    @Test
    public void commentRemove() {
        User user = new User();
        user.setId(1);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setAuthor(user);
        articleDao.add(article);

        Comment comment = new Comment();
        comment.setId(1);
        comment.setSource("Test comment");
        comment.setDate(System.currentTimeMillis());
        comment.setArticleId(1);
        comment.setUser(user);
        commentDao.add(comment);

        commentDao.removeById(1);
        Comment commentFromBd = commentDao.getById(1);
        assertEquals(0, commentFromBd.getId());
    }

    @Test
    public void commentUpdate() {
        User user = new User();
        user.setId(1);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setAuthor(user);
        articleDao.add(article);

        Comment comment = new Comment();
        comment.setId(1);
        comment.setSource("Test comment");
        comment.setDate(System.currentTimeMillis());
        comment.setArticleId(1);
        comment.setUser(user);
        commentDao.add(comment);

        comment.setSource("Updated comment");
        comment.setDate(System.currentTimeMillis() + 100L);
        user = new User();
        user.setId(2);
        userDao.add(user);
        comment.setUser(user);
        article = new Article();
        article.setId(2);
        article.setAuthor(user);
        articleDao.add(article);
        comment.setArticleId(2);

        commentDao.update(comment);

        Comment commentFromDb = commentDao.getById(1);
        assertEquals(comment, commentFromDb);
    }

    @Test
    public void commentGetAll() {
        List<Comment> comments = new ArrayList<>();
        for (char i = 1, j = 'a'; j < 'e'; j++, i++) {
            User user = new User();
            user.setId(i);
            userDao.add(user);

            Article article = new Article();
            article.setId(i);
            article.setAuthor(user);
            articleDao.add(article);

            Comment comment = new Comment();
            comment.setId(i);
            comment.setSource("" + j);
            comment.setDate(System.currentTimeMillis());
            comment.setArticleId(i);
            comment.setUser(user);
            commentDao.add(comment);
            comments.add(comment);
        }

        List<Comment> commentsFromDb = commentDao.getAll();
        assertEquals(comments, commentsFromDb);
    }
}
