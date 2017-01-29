/*
package ru.innopolis.course3;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.innopolis.course3.models.DBException;
import ru.innopolis.course3.models.article.Article;
import ru.innopolis.course3.models.role.RoleEntity;
import ru.innopolis.course3.models.user.UserEntity;
import ru.innopolis.course3.models.user.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

*/
/**
 * @author Danil Popov
 *//*


@ContextConfiguration("classpath:application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTests {

    private static final String CREATE_USER_TABLE = "CREATE TABLE P_USER" +
            "(" +
            " USER_ID SERIAL NOT NULL PRIMARY KEY," +
            " NAME TEXT UNIQUE NOT NULL," +
            " PASSWORD TEXT," +
            " SALT TEXT," +
            " IS_ACTIVE BOOLEAN," +
            " IS_ADMIN BOOLEAN," +
            " VERSION BIGINT" +
            " )";
    private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE ARTICLE" +
            "(" +
            " ARTICLE_ID SERIAL NOT NULL PRIMARY KEY," +
            " TITLE TEXT," +
            " SOURCE TEXT," +
            " DATE BIGINT," +
            " USER_ID INTEGER," +
            " UPDATE_DATE BIGINT," +
            " FOREIGN KEY (USER_ID) REFERENCES P_USER (USER_ID)" +
            " MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE" +
            " )";
    private static final String CREATE_COMMENT_TABLE = "CREATE TABLE COMMENT (" +
            "  COMMENT_ID SERIAL PRIMARY KEY NOT NULL," +
            "  SOURCE TEXT," +
            "  DATE BIGINT," +
            "  ARTICLE_ID INTEGER," +
            "  USER_ID INTEGER," +
            "  UPDATE_DATE BIGINT," +
            "  FOREIGN KEY (USER_ID) REFERENCES P_USER (USER_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE," +
            "  FOREIGN KEY (ARTICLE_ID) REFERENCES ARTICLE (ARTICLE_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE" +
            ")";
    private static final String DROP_ARTICLE_TABLE = "DROP TABLE ARTICLE";
    private static final String DROP_USER_TABLE = "DROP TABLE P_USER";
    private static final String DROP_COMMENT_TABLE = "DROP TABLE COMMENT";

    @Autowired
    private UserRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {
        entityManager.createNativeQuery(CREATE_USER_TABLE)
                .executeUpdate();
        entityManager.createNativeQuery(CREATE_ARTICLE_TABLE)
                .executeUpdate();
        entityManager.createNativeQuery(CREATE_COMMENT_TABLE)
                .executeUpdate();
    }

    @After
    public void drop() {
        entityManager.createNativeQuery(DROP_USER_TABLE)
                .executeUpdate();
        entityManager.createNativeQuery(DROP_ARTICLE_TABLE)
                .executeUpdate();
        entityManager.createNativeQuery(DROP_COMMENT_TABLE)
                .executeUpdate();
    }

    @Test
    public void userAddGet() throws DBException {

        List<RoleEntity> roles = new ArrayList<>();
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("ROLE_TEST1");
        roles.add(role);
        role = new RoleEntity();
        role.setId(2L);
        role.setName("ROLE_TEST2");
        roles.add(role);

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Mikola");
        user.setIsActive(true);
        user.setPassword("000101010011");
        user.setRoles(roles);

        repository.save(user);

        UserEntity userFromBd = repository.findOne(1L);
        assertEquals(user, userFromBd);
    }

    */
/*@Test
    public void userRemove() throws DBException {
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        user.setIsActive(true);
        user.setRoleId(1);
        user.setVersion(1);
        userDao.add(user);

        userDao.removeById(1, 1);

        User userFromBd = userDao.getById(1);
        assertEquals(0, userFromBd.getId());
    }

    @Test
    public void userUpdate() throws DBException {
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        user.setIsActive(true);
        user.setRoleId(1);
        userDao.add(user);

        user.setName("Test");
        user.setIsActive(false);
        user.setRoleId(1);
        userDao.update(user);

        User updatedUser = userDao.getById(1);
        user.setVersion(1);
        assertEquals(user, updatedUser);
    }

    @Test
    public void userGetAll() throws DBException {
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
    public void articleAddGet() throws DBException {
        long date = System.currentTimeMillis();

        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        user.setVersion(10);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setTitle("Test title");
        article.setSource("Loren ipsum dolore");
        article.setDate(date);
        article.setUpdateDate(date);
        article.setAuthor(user);
        articleDao.add(article);

        Article articleFromBd = articleDao.getById(1);
        assertEquals(article, articleFromBd);
    }

    @Test
    public void articleRemove() throws DBException {
        long date = System.currentTimeMillis();
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setTitle("Test title");
        article.setSource("Loren ipsum dolore");
        article.setDate(date);
        article.setAuthor(user);
        article.setUpdateDate(date);
        articleDao.add(article);

        articleDao.removeById(1, date);

        Article articleFromBd = articleDao.getById(1);
        assertEquals(0, articleFromBd.getId());
    }

    @Test
    public void articleUpdate() throws DBException {
        long date = System.currentTimeMillis();

        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setTitle("Test title");
        article.setSource("Loren ipsum dolore");
        article.setDate(date);
        article.setAuthor(user);
        articleDao.add(article);

        article.setTitle("updated title");
        article.setSource("updated source");
        article.setDate(date + 100L);
        articleDao.update(article);

        Article articleFromDb = articleDao.getById(1);
        assertEquals(article, articleFromDb);
    }

    @Test
    public void articleGetAll() throws DBException {
        List<Article> articles = new ArrayList<>();
        for (char i = 'a', j = 1; i < 'e'; i++, j++) {
            User user = new User();
            user.setId(j);
            user.setName(String.valueOf(i));
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
    public void commentAddGet() throws DBException {
        long date = System.currentTimeMillis();

        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        user.setVersion(0);
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setAuthor(user);
        article.setDate(date);
        article.setUpdateDate(date);
        articleDao.add(article);

        Comment comment = new Comment();
        comment.setId(1);
        comment.setSource("Test comment");
        comment.setDate(date);
        comment.setUpdateDate(date);
        comment.setArticleId(1);
        comment.setUser(user);
        commentDao.add(comment);

        Comment commentFromBd = commentDao.getById(1);
        assertEquals(comment, commentFromBd);
    }

    @Test
    public void commentRemove() throws DBException {
        long date = System.currentTimeMillis();
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
        userDao.add(user);

        Article article = new Article();
        article.setId(1);
        article.setAuthor(user);
        articleDao.add(article);

        Comment comment = new Comment();
        comment.setId(1);
        comment.setSource("Test comment");
        comment.setDate(date);
        comment.setUpdateDate(date);
        comment.setArticleId(1);
        comment.setUser(user);
        commentDao.add(comment);

        commentDao.removeById(1, date);
        Comment commentFromBd = commentDao.getById(1);
        assertEquals(0, commentFromBd.getId());
    }

    @Test
    public void commentUpdate() throws DBException {
        User user = new User();
        user.setId(1);
        user.setName("Mikola");
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
        user.setName("Henry");
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
    public void commentGetAll() throws DBException {
        List<Comment> comments = new ArrayList<>();
        for (char i = 1, j = 'a'; j < 'e'; j++, i++) {
            User user = new User();
            user.setId(i);
            user.setName(String.valueOf(j));
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
    }*//*

}
*/