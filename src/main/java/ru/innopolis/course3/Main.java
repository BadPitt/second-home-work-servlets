package ru.innopolis.course3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;

/**
 * @author Danil Popov
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("JDBC driver hasn\'t found", e);
            throw new RuntimeException();
        }
    }

    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/publishingis?user=postgres&password=postgres";

    private static final String CREATE_USER_TABLE = "CREATE TABLE P_USER" +
            "(USER_ID SERIAL NOT NULL PRIMARY KEY," +
            " NAME TEXT," +
            " PASSWORD TEXT," +
            " IS_ACTIVE BOOLEAN," +
            " ROLE BOOLEAN);";
    private static final String CREATE_ARTICLE_TABLE = "CREATE TABLE ARTICLE" +
            "(ARTICLE_ID SERIAL NOT NULL PRIMARY KEY," +
            " TITLE TEXT," +
            " SOURCE TEXT," +
            " DATE BIGINT," +
            " USER_ID INTEGER" +
            " CONSTRAINT article_p_user_user_id_fk\n" +
            " REFERENCES P_USER (USER_ID) ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String CREATE_COMMENT_TABLE = "CREATE TABLE COMMENT (" +
            "  COMMENT_ID SERIAL PRIMARY KEY NOT NULL," +
            "  SOURCE TEXT," +
            "  DATE BIGINT," +
            "  ARTICLE_ID INTEGER," +
            "  USER_ID INTEGER," +
            "  FOREIGN KEY (USER_ID) REFERENCES P_USER (USER_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE," +
            "  FOREIGN KEY (ARTICLE_ID) REFERENCES ARTICLE (ARTICLE_ID)" +
            "  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";
    private static String DROP_ARTICLE_TABLE = "DROP TABLE ARTICLE;";
    private static String DROP_USER_TABLE = "DROP TABLE P_USER;";
    private static String DROP_COMMENT_TABLE = "DROP TABLE COMMENT;";

    public static void main(String[] args) {
        /*createUserTable();
        createArticleTable();
        createCommentTable();*/
        Article article = new Article();
        article.setId(1);
        Article article2 = new Article();
        article2.setId(1);
        User user = new User();
        System.out.println(article.equals(article2));
        System.out.println(article.hashCode() + " " + article2.hashCode());
    }

    public static void createUserTable() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(CREATE_USER_TABLE);

        } catch (SQLException e) {
            logger.error("create USER table sql exception", e);
        }
    }

    public static void createArticleTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement statement = conn.createStatement()) {

            statement.execute(CREATE_ARTICLE_TABLE);

        } catch (SQLException e) {
            logger.error("create ARTICLE table sql exception", e);
        }
    }

    public static void dropArticleTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_ARTICLE_TABLE);

        } catch (SQLException e) {
            logger.error("drop ARTICLE table sql exception", e);
        }
    }

    public static void dropCommentTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_COMMENT_TABLE);

        } catch (SQLException e) {
            logger.error("drop COMMENT table sql exception", e);
        }
    }

    public static void dropUserTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement statement = conn.createStatement()) {

            statement.execute(DROP_USER_TABLE);

        } catch (SQLException e) {
            logger.error("drop USER table sql exception", e);
        }
    }

    public static void createCommentTable() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement statement = conn.createStatement()) {

            statement.execute(CREATE_COMMENT_TABLE);

        } catch (SQLException e) {
            logger.error("create COMMENT table sql exception", e);
        }
    }
}
